package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constant.MasterStatus;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.Subservice;
import ru.master.service.model.dto.*;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;
import ru.master.service.model.dto.response.AllMastersResDto;
import ru.master.service.model.dto.response.MasterInfoForProfileResDto;
import ru.master.service.model.dto.response.MasterProfileResDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MasterProfileMapperImpl implements MasterProfileMapper {


    @Override
    public MasterProfileForCreateDto toMasterProfileForCreateDto(CreateMasterProfileReqDto dto) {

        var cityDto = CityDto.builder()
                .id(dto.getCityId())
                .build();

        var userAgreementDto = UserAgreementDto.builder()
                .personalDataConsent(dto.isPersonalDataConsent())
                .notificationsAllowed(dto.isNotificationsAllowed())
                .locationAccessAllowed(dto.isLocationAccessAllowed())
                .serviceTermsAccepted(dto.isServiceTermsAccepted())
                .serviceRulesAccepted(dto.isServiceRulesAccepted())
                .build();

        List<MasterSubserviceDto> masterSubserviceDtos = dto.getSubServiceIds().stream()
                .map(sub -> MasterSubserviceDto.builder()
                                .id(sub)
                                .build())
                .collect(Collectors.toList());


        return MasterProfileForCreateDto.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .workExperience(dto.getWorkExperience())
                .hasConviction(dto.isHasConviction())
                .maritalStatus(dto.getMaritalStatus())
                .education(dto.getEducation())
                .cityDto(cityDto)
                .userAgreementDto(userAgreementDto)
                .masterSubserviceDtos(masterSubserviceDtos)
                .build();
    }

    @Override
    public MasterProfile toMasterProfileEntity(MasterProfileForCreateDto dto,
                                               User user,
                                               City city,
                                               List<Subservice> subservices) {
        if (dto == null) return null;

        return MasterProfile.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .hasConviction(dto.isHasConviction())
                .workExperience(dto.getWorkExperience())
                .maritalStatus(dto.getMaritalStatus())
                .education(dto.getEducation())
                .subservices(subservices)
                .masterStatus(MasterStatus.OFFLINE)
                .averageRating(0.0F)
                .ratingCount(0L)
                .balance(BigDecimal.valueOf(0.00))
                .city(city)
                .user(user)
                .build();
    }

    @Override
    public MasterInfoForProfileResDto toMasterInfoForProfileResDto(MasterProfile master) {
        if (master == null) return null;

        return MasterInfoForProfileResDto.builder()
                .id(master.getId())
                .firstName(master.getFirstName())
                .lastName(master.getLastName())
                .averageRating(master.getAverageRating())
                .ratingCount(master.getRatingCount())
                .balance(master.getBalance())
                .build();
    }

    @Override
    public AllMastersResDto toAllMastersResDto(MasterProfile masterProfile, int completedOrderCount) {
        if (masterProfile == null) return null;

        return AllMastersResDto.builder()
                .id(masterProfile.getId())
                .firstName(masterProfile.getFirstName())
                .lastName(masterProfile.getLastName())
                .phoneNumber(masterProfile.getUser().getPhoneNumber())
                .completedOrdersCount(completedOrderCount)
                .build();
    }

    @Override
    public MasterProfileResDto toMasterProfileResDto(MasterProfile entity,
                                                     int completedOrdersThisMonth,
                                                     int totalCompletedOrders
    ) {
        if (entity == null) return null;

        var userDto = UserDto.builder()
                .id(entity.getUser().getId())
                .phoneNumber(entity.getUser().getPhoneNumber())
                .verificationStatus(entity.getUser().getVerificationStatus())
                .createdAt(entity.getUser().getCreatedAt())
                .updatedAt(entity.getUser().getUpdatedAt())
                .build();

        var cityDto = CityDto.builder()
                .id(entity.getCity().getId())
                .name(entity.getCity().getName())
                .build();

        List<MasterSubserviceDto> masterSebserviceDtos = entity.getSubservices().stream()
                .map(sub -> MasterSubserviceDto.builder()
                        .id(sub.getId())
                        .name(sub.getName())
                        .createdAt(sub.getCreatedAt())
                        .updatedAt(sub.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        var masterInfoDto = MasterProfileForMasterRequestResDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .education(entity.getEducation())
                .maritalStatus(entity.getMaritalStatus())
                .cityDto(cityDto)
                .subserviceDtos(masterSebserviceDtos)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        return MasterProfileResDto.builder()
                .userDto(userDto)
                .masterInfoDto(masterInfoDto)
                .completedOrdersThisMonth(completedOrdersThisMonth)
                .totalCompletedOrders(totalCompletedOrders)
                .build();
    }
}
