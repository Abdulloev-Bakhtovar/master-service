package ru.master.service.service;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.constant.DocumentType;

import java.io.IOException;
import java.util.UUID;

public interface FileStorageService {

    void storeFile(MultipartFile file, DocumentType docType, UUID entityId) throws IOException;

    byte[] loadFile(DocumentType docType, UUID entityId);

    MediaType getMediaType(DocumentType docType, UUID entityId);

}
