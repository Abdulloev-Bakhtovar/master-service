package ru.master.service.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.config.DocFileProperties;
import ru.master.service.constant.DocumentType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.model.dto.response.ImageResDto;
import ru.master.service.service.FileStorageService;
import ru.master.service.util.AuthUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final DocFileProperties docFileProp;
    private final AuthUtil authUtil;
    private Path imagesRootLocation;

    @PostConstruct
    public void init() throws IOException {
        // Основной путь: filePath + imagesSubDir
        this.imagesRootLocation = Paths.get(docFileProp.getFilePath())
                .resolve(docFileProp.getImagesSubDir());

        if (!Files.exists(imagesRootLocation)) {
            Files.createDirectories(imagesRootLocation);
        }

        // Создаем все подкаталоги для каждого типа документа
        for (DocumentType type : DocumentType.values()) {
            Path typeDir = imagesRootLocation.resolve(type.getSubDirectory());
            if (!Files.exists(typeDir)) {
                Files.createDirectories(typeDir);
            }
        }
    }

    @Override
    public void storeFile(MultipartFile file, DocumentType docType, UUID entityId) throws IOException {
        if (file == null || file.isEmpty()) {
            return;
        }

        String contentType = file.getContentType();
        String extension = getExtension(file, contentType);

        // Формирование имени файла
        String filename = docType.getPrefix() + "_" + entityId + extension;

        // Полный путь: корень + подкаталог типа + имя файла
        Path destination = imagesRootLocation
                .resolve(docType.getSubDirectory())
                .resolve(filename);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public byte[] loadFile(DocumentType docType, UUID entityId) {
        String filenamePrefix = docType.getPrefix() + "_" + entityId;

        Path dir = imagesRootLocation.resolve(docType.getSubDirectory());

        try {
            // Найти файл по префиксу (расширение может быть разным)
            try (var files = Files.list(dir)) {
                Path filePath = files
                        .filter(p -> p.getFileName().toString().startsWith(filenamePrefix))
                        .findFirst()
                        .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));

                return Files.readAllBytes(filePath);
            }
        } catch (IOException e) {
            throw new AppException("Error loading file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MediaType getMediaType(DocumentType docType, UUID entityId) {
        String filenamePrefix = docType.getPrefix() + "_" + entityId;
        Path dir = imagesRootLocation.resolve(docType.getSubDirectory());

        try (var files = Files.list(dir)) {
            Path filePath = files
                    .filter(p -> p.getFileName().toString().startsWith(filenamePrefix))
                    .findFirst()
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.FILE_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));

            String fileName = filePath.getFileName().toString();
            if (fileName.endsWith(".png")) return MediaType.IMAGE_PNG;
            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;

            return MediaType.APPLICATION_OCTET_STREAM;
        } catch (IOException e) {
            throw new AppException("Error determining media type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ImageResDto> getMasterDocuments() {

        var master = authUtil.getAuthenticatedUser();
        UUID masterId = master.getId();

        List<ImageResDto> result = new ArrayList<>();

        // Только документы мастера
        List<DocumentType> masterDocs = List.of(
                DocumentType.PASSPORT_MAIN,
                DocumentType.PASSPORT_REGISTRATION,
                DocumentType.SNILS,
                DocumentType.INN
        );

        for (DocumentType docType : masterDocs) {
            Path dir = imagesRootLocation.resolve(docType.getSubDirectory());
            String prefix = docType.getPrefix() + "_" + masterId;

            try (var files = Files.list(dir)) {
                files.filter(p -> p.getFileName().toString().startsWith(prefix))
                        .findFirst()
                        .ifPresent(path -> {
                            try {
                                byte[] data = Files.readAllBytes(path);
                                MediaType mediaType = this.getMediaType(docType, masterId);
                                result.add(new ImageResDto(masterId, mediaType.toString(), data));
                            } catch (IOException e) {
                                throw new AppException(
                                        "Failed to read file: " + path.getFileName(),
                                        HttpStatus.INTERNAL_SERVER_ERROR
                                );
                            }
                        });
            } catch (IOException e) {
                throw new AppException(
                        "Error reading files for master: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }
        }

        return result;
    }

    @Override
    public ImageResDto getMasterProfileImage() {
        UUID masterId = authUtil.getAuthenticatedUser().getId(); // TODO проверит корректности работу

        byte[] data = loadFile(DocumentType.PROFILE, masterId);
        MediaType mediaType = getMediaType(DocumentType.PROFILE, masterId);

        return new ImageResDto(masterId, mediaType.toString(), data);
    }

    @Override
    public ImageResDto getNewsImage(UUID id) {
        byte[] data = loadFile(DocumentType.NEWS_PHOTO, id);
        MediaType mediaType = getMediaType(DocumentType.NEWS_PHOTO, id);

        return new ImageResDto(id, mediaType.toString(), data);
    }

    private String getExtension(MultipartFile file, String contentType) {
        if (!docFileProp.getAllowedTypes().contains(contentType)) {
            throw new AppException(
                    ErrorMessage.UNSUPPORTED_FILE_TYPE
                            + " Allowed types: " + docFileProp.getAllowedTypes()
                            + ". Received: " + contentType,
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new AppException(
                    ErrorMessage.INVALID_FILE_NAME,
                    HttpStatus.BAD_REQUEST);
        }

        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}