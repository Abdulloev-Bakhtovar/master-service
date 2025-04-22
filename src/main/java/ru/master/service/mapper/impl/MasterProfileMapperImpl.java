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
import ru.master.service.model.dto.MasterProfileDto;

import java.util.List;

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
}
