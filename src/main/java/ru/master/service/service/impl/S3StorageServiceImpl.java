package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.config.S3Config;
import ru.master.service.constant.DocumentType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.model.dto.response.ImageResDto;
import ru.master.service.service.S3StorageService;
import ru.master.service.util.AuthUtil;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageServiceImpl implements S3StorageService {

    private final S3Client s3Client;
    private final S3Config s3Config;
    private final AuthUtil authUtil;

    @Override
    public void storeFile(MultipartFile file, DocumentType docType, UUID entityId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorMessage.FILE_IS_EMPTY, HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        validateContentType(contentType);

        String key = buildS3Key(docType, entityId, file.getOriginalFilename());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Config.getBucket())
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
    }

    @Override
    public byte[] loadFile(DocumentType docType, UUID entityId) {
        String keyPrefix = buildS3KeyPrefix(docType, entityId);

        String objectKey = findObjectKeyByPrefix(keyPrefix);

        return s3Client.getObjectAsBytes(GetObjectRequest.builder()
                        .bucket(s3Config.getBucket())
                        .key(objectKey)
                        .build())
                .asByteArray();
    }

    @Override
    public MediaType getMediaType(DocumentType docType, UUID entityId) {
        String keyPrefix = buildS3KeyPrefix(docType, entityId);

        try {
            String objectKey = findObjectKeyByPrefix(keyPrefix);
            return determineMediaType(objectKey);
        } catch (Exception e) {
            throw new AppException(ErrorMessage.MEDIA_TYPE_DETERMINATION_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ImageResDto> getMasterDocuments() {
        UUID masterId = authUtil.getAuthenticatedUser().getId();
        List<ImageResDto> result = new ArrayList<>();

        List<DocumentType> masterDocs = List.of(
                DocumentType.PASSPORT_MAIN,
                DocumentType.PASSPORT_REGISTRATION,
                DocumentType.SNILS,
                DocumentType.INN
        );

        for (DocumentType docType : masterDocs) {
            try {
                String keyPrefix = buildS3KeyPrefix(docType, masterId);
                String objectKey = findObjectKeyByPrefix(keyPrefix);

                byte[] data = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                                .bucket(s3Config.getBucket())
                                .key(objectKey)
                                .build())
                        .asByteArray();

                MediaType mediaType = determineMediaType(objectKey);
                result.add(new ImageResDto(masterId, mediaType.toString(), data));
            } catch (Exception e) {
                // Пропускаем документы, которые не найдены
                continue;
            }
        }

        return result;
    }

    @Override
    public ImageResDto getMasterProfileImage() {
        UUID masterId = authUtil.getAuthenticatedUser().getId();
        return getImage(DocumentType.PROFILE, masterId);
    }

    @Override
    public ImageResDto getNewsImage(UUID id) {
        return getImage(DocumentType.NEWS_PHOTO, id);
    }

    private ImageResDto getImage(DocumentType docType, UUID entityId) {
        try {
            String keyPrefix = buildS3KeyPrefix(docType, entityId);
            String objectKey = findObjectKeyByPrefix(keyPrefix);

            byte[] data = s3Client.getObjectAsBytes(GetObjectRequest.builder()
                            .bucket(s3Config.getBucket())
                            .key(objectKey)
                            .build())
                    .asByteArray();

            MediaType mediaType = determineMediaType(objectKey);
            return new ImageResDto(entityId, mediaType.toString(), data);
        } catch (Exception e) {
            throw new AppException(ErrorMessage.FILE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    private String buildS3Key(DocumentType docType, UUID entityId, String originalFilename) {
        validateFilename(originalFilename);
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return docType.getSubDirectory() + "/" + docType.getPrefix() + "_" + entityId + extension;
    }

    private String buildS3KeyPrefix(DocumentType docType, UUID entityId) {
        return docType.getSubDirectory() + "/" + docType.getPrefix() + "_" + entityId;
    }

    private String findObjectKeyByPrefix(String prefix) {
        ListObjectsRequest listRequest = ListObjectsRequest.builder()
                .bucket(s3Config.getBucket())
                .prefix(prefix)
                .build();

        List<S3Object> objects = s3Client.listObjects(listRequest).contents();

        return objects.stream()
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorMessage.FILE_NOT_FOUND, HttpStatus.NOT_FOUND))
                .key();
    }

    private MediaType determineMediaType(String objectKey) {
        if (objectKey.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        }
        if (objectKey.endsWith(".jpg") || objectKey.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    private void validateContentType(String contentType) {
        if (contentType == null || !s3Config.getAllowedTypes().contains(contentType)) {
            throw new AppException(
                    String.format("%s Allowed types: %s. Received: %s",
                            ErrorMessage.UNSUPPORTED_FILE_TYPE,
                            s3Config.getAllowedTypes(),
                            contentType),
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private void validateFilename(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new AppException(ErrorMessage.INVALID_FILE_NAME, HttpStatus.BAD_REQUEST);
        }
    }
}