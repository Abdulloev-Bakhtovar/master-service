package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constant.MasterStatus;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.Subservice;
import ru.master.service.model.dto.CityDto;
import ru.master.service.model.dto.MasterProfileForCreateDto;
import ru.master.service.model.dto.MasterSubserviceDto;
import ru.master.service.model.dto.UserAgreementDto;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;

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
                .city(city)
                .user(user)
                .build();
    }
}
