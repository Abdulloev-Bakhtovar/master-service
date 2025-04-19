package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.mapper.UserAgreementMapper;
import ru.master.service.model.dto.MasterProfileDto;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.service.CityService;
import ru.master.service.service.DocumentPhotoStorageService;
import ru.master.service.service.MasterProfileService;
import ru.master.service.service.UserAgreementService;
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
    private final DocumentPhotoStorageService docPhotoStorageService;
    private final UserAgreementMapper userAgreementMapper;

    @Override
    public void create(MasterProfileDto dto) throws IOException {
        var user = authUtils.getAuthenticatedUser();
        var userId = user.getId();
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

        var userAgreementDto = userAgreementMapper.toDto(dto);
        userAgreementService.create(userAgreementDto, user);
        
        var city = cityService.getById(dto.getCityId());
        var masterProfile = masterProfileMapper.toEntity(dto, user, city);
        masterProfileRepo.save(masterProfile);

        docPhotoStorageService.storeFile(dto.getProfilePhoto(), "profile", userId);
        docPhotoStorageService.storeFile(dto.getPassportMainPhoto(), "passport_main", userId);
        docPhotoStorageService.storeFile(dto.getPassportRegistrationPhoto(), "passport_registration", userId);
        docPhotoStorageService.storeFile(dto.getSnilsPhoto(), "snils", userId);
        docPhotoStorageService.storeFile(dto.getInnPhoto(), "inn", userId);
    }
}
