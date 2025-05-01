package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.UserService;
import ru.master.service.constant.DocumentType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.Role;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.request.CreateMasterProfileDto;
import ru.master.service.repository.ClientOrderRepo;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.service.*;
import ru.master.service.util.AuthUtil;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterProfileServiceImpl implements MasterProfileService {

    private final MasterProfileRepo masterProfileRepo;
    private final ClientOrderRepo clientOrderRepo;
    private final AuthUtil authUtil;
    private final MasterProfileMapper masterProfileMapper;
    private final UserRepo userRepo;
    private final UserAgreementService userAgreementService;
    private final CityService cityService;
    private final FileStorageService docPhotoStorageService;
    private final UserService userService;
    private final MasterSubServiceService masterSubServiceService;
    private final MasterRequestService masterRequestService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CreateMasterProfileDto reqDto) throws IOException {
        var masterProfileDto = masterProfileMapper.toCreateProfileDto(reqDto);

        var user = authUtil.getAuthenticatedUser();
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

        userAgreementService.create(masterProfileDto.getUserAgreementDto(), user);

        var city = cityService.getById(masterProfileDto.getCityDto().getId());
        var masterProfile = masterProfileMapper.toEntity(masterProfileDto, user, city);

        addDocFile(reqDto, userId);

        masterProfile = masterProfileRepo.save(masterProfile);
        masterSubServiceService.create(masterProfileDto.getServiceCategoryDtos(), masterProfile);
        //userService.updateVerificationStatus(user, VerificationStatus.UNDER_REVIEW);
        masterRequestService.create(user);
    }

    private void addDocFile(CreateMasterProfileDto dto, UUID userId) throws IOException {
        docPhotoStorageService.storeFile(dto.getProfilePhoto(), DocumentType.PROFILE, userId);
        docPhotoStorageService.storeFile(dto.getPassportMainPhoto(), DocumentType.PASSPORT_MAIN, userId);
        docPhotoStorageService.storeFile(dto.getPassportRegistrationPhoto(), DocumentType.PASSPORT_REGISTRATION, userId);
        docPhotoStorageService.storeFile(dto.getSnilsPhoto(), DocumentType.SNILS, userId);
        docPhotoStorageService.storeFile(dto.getInnPhoto(), DocumentType.INN, userId);
    }

    @Override
    public void updateMasterAverageRating(MasterProfile master, float newRating) {

        if (newRating < 1 || newRating > 5) {
            throw new AppException(
                    "Rating must be between 1 and 5",
                    HttpStatus.BAD_REQUEST
            );
        }

        // Получаем все завершенные заказы мастера с оценками
        /*List<ClientOrder> completedOrders = clientOrderRepo.findByMasterProfileAndClientRatingIsNotNull(master);

        // Добавляем новую оценку к списку
        List<Float> allRatings = completedOrders.stream()
                .map(ClientOrder::getClientRating)
                .collect(Collectors.toList());
        allRatings.add(newRating);

        // Вычисляем новый средний рейтинг
        double average = allRatings.stream()
                .mapToDouble(Float::doubleValue)
                .average()
                .orElse(0.0);

        // Обновляем профиль мастера
        master.setAverageRating((float) average);
        masterProfileRepo.save(master);*/
    }
}
