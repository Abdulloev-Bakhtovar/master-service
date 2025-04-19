package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.AuthService;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.constants.Role;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.model.dto.DocFileDto;
import ru.master.service.model.dto.MasterProfileDto;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.service.*;
import ru.master.service.util.AuthUtils;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterProfileServiceImpl implements MasterProfileService {

    private final UserRepo userRepo;
    private final AuthUtils authUtils;
    private final CityService cityService;
    private final UserAgreementService userAgreementService;
    private final MasterProfileRepo masterProfileRepo;
    private final MasterProfileMapper masterProfileMapper;
    private final DocumentFileStorageService docPhotoStorageService;
    private final MasterSubServiceService masterSubServiceService;
    private final AuthService authService;

    @Override
    public void create(MasterProfileDto dto) {
        var user = authUtils.getAuthenticatedUser();
        var userId = user.getId();

        if (user.getRole() == Role.CLIENT || user.getRole() ==  Role.ADMIN) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }

        user = userRepo.findById(userId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        if (masterProfileRepo.existsByUserId(userId)) {
            throw new AppException(
                    ErrorMessage.MASTER_PROFILE_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        userAgreementService.create(dto.getUserAgreementDto(), user);
        
        var city = cityService.getById(dto.getCityDto().getId());
        var masterProfile = masterProfileMapper.toEntity(dto, user, city);

        masterProfile = masterProfileRepo.save(masterProfile);

        masterSubServiceService.create(dto.getServiceCategoryDtos(), masterProfile);

        authService.updateVerificationStatus(user, VerificationStatus.INFO_ENTERED);
    }

    @Override
    public void addDocFile(DocFileDto docFileDto) throws IOException {
        var user = authUtils.getAuthenticatedUser();
        var userId = user.getId();

        if (!userRepo.existsById(userId)) {
            throw new AppException(
                    ErrorMessage.USER_NOT_FOUND,
                    HttpStatus.NOT_FOUND
            );
        }

        docPhotoStorageService.storeFile(docFileDto.getProfilePhoto(), "profile", userId);
        docPhotoStorageService.storeFile(docFileDto.getPassportMainPhoto(), "passport_main", userId);
        docPhotoStorageService.storeFile(docFileDto.getPassportRegistrationPhoto(), "passport_registration", userId);
        docPhotoStorageService.storeFile(docFileDto.getSnilsPhoto(), "snils", userId);
        docPhotoStorageService.storeFile(docFileDto.getInnPhoto(), "inn", userId);

        authService.updateVerificationStatus(user, VerificationStatus.DOCS_UPLOADED);
    }
}
