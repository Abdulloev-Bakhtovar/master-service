package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.UserService;
import ru.master.service.constant.DocumentType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.model.MasterFeedback;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.Subservice;
import ru.master.service.model.dto.MasterProfileForCreateDto;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;
import ru.master.service.repository.MasterFeedbackRepo;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.repository.OrderRepo;
import ru.master.service.repository.SubserviceRepo;
import ru.master.service.service.*;
import ru.master.service.util.AuthUtil;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterProfileServiceImpl implements MasterProfileService {

    private final MasterProfileRepo masterProfileRepo;
    private final AuthUtil authUtil;
    private final MasterProfileMapper masterProfileMapper;
    private final UserRepo userRepo;
    private final UserAgreementService userAgreementService;
    private final CityService cityService;
    private final FileStorageService docPhotoStorageService;
    private final UserService userService;
    private final SubserviceRepo subserviceRepo;
    private final MasterRequestService masterRequestService;
    private final OrderRepo orderRepo;
    private final MasterFeedbackRepo masterFeedbackRepo;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CreateMasterProfileReqDto reqDto) throws IOException {
        MasterProfileForCreateDto masterProfileDto = masterProfileMapper.toMasterProfileForCreateDto(reqDto);

        var user = authUtil.getAuthenticatedUser();
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

        userAgreementService.create(masterProfileDto.getUserAgreementDto(), user);

        List<Subservice> masterSubservices = subserviceRepo.findAllByIdIn(reqDto.getSubServiceIds());
        var city = cityService.getById(masterProfileDto.getCityDto().getId());
        var masterProfile = masterProfileMapper.toMasterProfileEntity(masterProfileDto, user, city, masterSubservices);

        addDocFile(reqDto, userId);

        masterProfileRepo.save(masterProfile);
        masterRequestService.create(user);
    }

    @Override
    public void updateMasterAverageRating(MasterProfile master, Float newRating) {

        if (newRating < 1 || newRating > 5) {
            throw new AppException(
                    "Rating must be between 1 and 5",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Получаем все завершенные отзывы мастера с оценками
        List<MasterFeedback> feedbacks = masterFeedbackRepo.findByMasterIdAndRatingIsNotNull(master.getId());

        // Собираем все оценки
        List<Float> allRatings = feedbacks.stream()
                .map(MasterFeedback::getRating)
                .collect(Collectors.toList());
        allRatings.add(newRating);

        // Вычисляем новый средний рейтинг
        double average = allRatings.stream()
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0.0);

        // Обновляем профиль мастера
        master.setAverageRating((float) average);
        masterProfileRepo.save(master);
    }


    private void addDocFile(CreateMasterProfileReqDto dto, UUID userId) throws IOException {
        docPhotoStorageService.storeFile(dto.getProfilePhoto(), DocumentType.PROFILE, userId);
        docPhotoStorageService.storeFile(dto.getPassportMainPhoto(), DocumentType.PASSPORT_MAIN, userId);
        docPhotoStorageService.storeFile(dto.getPassportRegistrationPhoto(), DocumentType.PASSPORT_REGISTRATION, userId);
        docPhotoStorageService.storeFile(dto.getSnilsPhoto(), DocumentType.SNILS, userId);
        docPhotoStorageService.storeFile(dto.getInnPhoto(), DocumentType.INN, userId);
    }
}
