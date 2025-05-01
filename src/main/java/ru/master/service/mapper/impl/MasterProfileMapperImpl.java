package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.mapper.CityMapper;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.mapper.UserAgreementMapper;
import ru.master.service.model.*;
import ru.master.service.model.dto.*;
import ru.master.service.model.dto.request.CreateMasterProfileDto;
import ru.master.service.model.dto.response.NewMasterRequestDto;

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
    public MasterInfoForClientOrderDto toOrderInfoForClientDto(MasterProfile entity) {
        if (entity == null) return null;

        return MasterInfoForClientOrderDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .averageRating(entity.getAverageRating())
                .build();
    }

    @Override
    public MasterProfile toEntity(MasterProfileForCreateDto dto, User user, City city) {
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
    public NewMasterRequestDto toNewMasterRequestDto(MasterProfile masterProfile, MasterRequest masterRequest) {
        if (masterProfile == null) return null;

        return NewMasterRequestDto.builder()
                .id(masterRequest.getId())
                .firstName(masterProfile.getFirstName())
                .lastName(masterProfile.getLastName())
                .createdAt(masterProfile.getCreatedAt())
                .updatedAt(masterProfile.getUpdatedAt())
                .phoneNumber(masterProfile.getUser().getPhoneNumber())
                .workExperience(masterProfile.getWorkExperience())
                .cityDto(cityMapper.toDto(masterProfile.getCity()))
                .build();
    }

    @Override
    public MasterProfileForMasterRequestDto toMasterRequestDto(MasterProfile entity,
                                                               List<MasterSubService> masterSubServices,
                                                               UserAgreement userAgreement) {
        if (entity == null) return null;

        return MasterProfileForMasterRequestDto.builder()
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
    public MasterProfileForCreateDto toCreateProfileDto(CreateMasterProfileDto reqDto) {
        return buildMasterProfileDto(reqDto);
    }

    private MasterProfileForCreateDto buildMasterProfileDto(CreateMasterProfileDto dto) {
        return MasterProfileForCreateDto.builder()
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
                .build();
    }

    private CityDto buildCityDto(UUID cityId) {
        return CityDto.builder().id(cityId).build();
    }

    private UserAgreementDto buildUserAgreementDto(CreateMasterProfileDto dto) {
        return UserAgreementDto.builder()
                .personalDataConsent(dto.isPersonalDataConsent())
                .notificationsAllowed(dto.isNotificationsAllowed())
                .locationAccessAllowed(dto.isLocationAccessAllowed())
                .serviceTermsAccepted(dto.isServiceTermsAccepted())
                .serviceRulesAccepted(dto.isServiceRulesAccepted())
                .build();
    }

    private List<ServiceCategoryForMasterProfileDto> buildServiceCategoryDtos(CreateMasterProfileDto dto) {
        List<SubServiceForServiceDto> subServiceCategories = dto.getSubServiceCategoryIds().stream()
                .map(this::buildSubServiceCategoryDto)
                .collect(Collectors.toList());

        return dto.getServiceCategoryIds().stream()
                .map(id -> ServiceCategoryForMasterProfileDto.builder()
                        .id(id)
                        .subServiceCategoryDtos(subServiceCategories)
                        .build())
                .collect(Collectors.toList());
    }

    private SubServiceForServiceDto buildSubServiceCategoryDto(UUID id) {
        return SubServiceForServiceDto.builder()
                .id(id)
                .build();
    }
}
