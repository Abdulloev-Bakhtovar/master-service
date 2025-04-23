package ru.master.service.service;

import org.springframework.web.multipart.MultipartFile;
import ru.master.service.constants.DocumentType;

import java.io.IOException;
import java.util.UUID;

public interface DocumentFileStorageService {

    void storeFile(MultipartFile file, DocumentType docType, UUID entityId) throws IOException;
}
