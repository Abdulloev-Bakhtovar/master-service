package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.mapper.CityMapper;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.mapper.UserAgreementMapper;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.UserAgreement;
import ru.master.service.model.dto.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MasterProfileMapperImpl implements MasterProfileMapper {

    private final CityMapper cityMapper;
    private final UserAgreementMapper userAgreementMapper;
    private final ServiceCategoryMapper serviceCategoryMapper;

    @Override
    public MasterProfile toEntity(MasterProfileDto dto, User user, City city) {
        if (dto == null) return null;

        return MasterProfile.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .hasConviction(dto.isHasConviction())
                .workExperience(dto.getWorkExperience())
                .maritalStatus(dto.getMaritalStatus())
                .education(dto.getEducation())
                .city(city)
                .user(user)
                .build();
    }

    @Override
    public MasterProfileDto toDto(MasterProfile entity,
                                  List<MasterSubService> masterSubServices,
                                  UserAgreement userAgreement
    ) {
        if (entity == null) return null;

        return MasterProfileDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .email(entity.getEmail())
                .hasConviction(entity.isHasConviction())
                .workExperience(entity.getWorkExperience())
                .maritalStatus(entity.getMaritalStatus())
                .education(entity.getEducation())
                .cityDto(cityMapper.toDto(entity.getCity()))
                .serviceCategoryDtos(serviceCategoryMapper.toDtoList(masterSubServices))
                .userAgreementDto(userAgreementMapper.toDto(userAgreement))
                .build();
    }

    @Override
    public MasterProfileDto toDto(MasterProfileCreateDto dto) {
        return buildMasterProfileDto(dto);
    }

    @Override
    public NewMasterRequestDto toDto(MasterProfile masterProfile) {
        if (masterProfile == null) return null;

        return NewMasterRequestDto.builder()
                .id(masterProfile.getId())
                .firstName(masterProfile.getFirstName())
                .lastName(masterProfile.getLastName())
                .phoneNumber(masterProfile.getUser().getPhoneNumber())
                .workExperience(masterProfile.getWorkExperience())
                .cityDto(cityMapper.toDto(masterProfile.getCity()))
                .build();
    }

    private MasterProfileDto buildMasterProfileDto(MasterProfileCreateDto dto) {
        return MasterProfileDto.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .workExperience(dto.getWorkExperience())
                .hasConviction(dto.isHasConviction())
                .maritalStatus(dto.getMaritalStatus())
                .education(dto.getEducation())
                .cityDto(buildCityDto(dto.getCityId()))
                .userAgreementDto(buildUserAgreementDto(dto))
                .serviceCategoryDtos(buildServiceCategoryDtos(dto))
                .profilePhoto(dto.getProfilePhoto())
                .passportMainPhoto(dto.getPassportMainPhoto())
                .passportRegistrationPhoto(dto.getPassportRegistrationPhoto())
                .snilsPhoto(dto.getSnilsPhoto())
                .innPhoto(dto.getInnPhoto())
                .build();
    }

    private CityDto buildCityDto(UUID cityId) {
        return CityDto.builder().id(cityId).build();
    }

    private UserAgreementDto buildUserAgreementDto(MasterProfileCreateDto dto) {
        return UserAgreementDto.builder()
                .personalDataConsent(dto.isPersonalDataConsent())
                .notificationsAllowed(dto.isNotificationsAllowed())
                .locationAccessAllowed(dto.isLocationAccessAllowed())
                .serviceTermsAccepted(dto.getServiceTermsAccepted())
                .serviceRulesAccepted(dto.getServiceRulesAccepted())
                .build();
    }

    private List<ServiceCategoryDto> buildServiceCategoryDtos(MasterProfileCreateDto dto) {
        List<SubServiceCategoryDto> subServiceCategories = dto.getSubServiceCategoryIds().stream()
                .map(this::buildSubServiceCategoryDto)
                .collect(Collectors.toList());

        return dto.getServiceCategoryIds().stream()
                .map(id -> ServiceCategoryDto.builder()
                        .id(id)
                        .subServiceCategoryDtos(subServiceCategories)
                        .build())
                .collect(Collectors.toList());
    }

    private SubServiceCategoryDto buildSubServiceCategoryDto(UUID id) {
        return SubServiceCategoryDto.builder().id(id).build();
    }
}
