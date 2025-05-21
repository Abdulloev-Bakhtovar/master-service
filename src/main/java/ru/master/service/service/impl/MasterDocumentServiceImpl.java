package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.DocumentType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.model.MasterDocument;
import ru.master.service.model.dto.request.MasterDocumentReqDto;
import ru.master.service.model.dto.response.MasterDocumentResDto;
import ru.master.service.repository.MasterDocumentRepo;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.service.MasterDocumentService;
import ru.master.service.service.S3StorageService;
import ru.master.service.util.AuthUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterDocumentServiceImpl implements MasterDocumentService {

    private final MasterDocumentRepo masterDocumentRepo;
    private final S3StorageService docPhotoStorageService;
    private final AuthUtil authUtil;
    private final MasterProfileRepo masterProfileRepo;

    @Override
    public void add(MasterDocumentReqDto reqDto) throws IOException {
        var userId = authUtil.getAuthenticatedUser().getId();

        var master = masterProfileRepo.findByUserId(userId)
                        .orElseThrow(() -> new AppException(
                                ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        ));
        var masterDocument = MasterDocument.builder()
                .master(master)
                .type(reqDto.getType())
                .description(reqDto.getDescription())
                .build();

        masterDocumentRepo.save(masterDocument);

        docPhotoStorageService.storeFile(reqDto.getFile(), DocumentType.OTHER, masterDocument.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MasterDocumentResDto> getAll(UUID masterId) {

        var master = masterProfileRepo.findById(masterId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var documents = masterDocumentRepo.findAllByMaster(master);

        if (documents.isEmpty()) {
            return new ArrayList<>();
        }

        return documents.stream()
                .map(doc -> MasterDocumentResDto.builder()
                        .id(doc.getId())
                        .type(doc.getType())
                        .description(doc.getDescription())
                        .createdAt(doc.getCreatedAt())
                        .updatedAt(doc.getUpdatedAt())
                        .masterId(doc.getMaster().getId())
                        .build())
                .collect(Collectors.toList());
    }

}
