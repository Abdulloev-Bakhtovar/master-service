package ru.master.service.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.config.DocFileProperties;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.service.DocumentFileStorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentFileStorageServiceImpl implements DocumentFileStorageService {

    private final DocFileProperties docFileProp;
    private Path rootLocation;

    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(docFileProp.getFilePath());
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
    }

    @Override
    public void storeFile(MultipartFile file, String prefix, UUID userId) throws IOException {
        if (file == null || file.isEmpty()) {
            return;
        }

        String contentType = file.getContentType();
        String extension = getExtension(file, contentType);
        String filename = prefix + "_" + userId + extension;

        Path destination = rootLocation.resolve(filename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
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
                    HttpStatus.BAD_REQUEST
            );
        }

        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
