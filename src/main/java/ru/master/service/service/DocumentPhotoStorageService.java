package ru.master.service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface DocumentPhotoStorageService {

    void storeFile(MultipartFile file, String prefix, UUID userId) throws IOException;
}
