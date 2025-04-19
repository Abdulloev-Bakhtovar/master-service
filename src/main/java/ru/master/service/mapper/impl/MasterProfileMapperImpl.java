package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.MasterProfileDto;

@Component
public class MasterProfileMapperImpl implements MasterProfileMapper {

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
}
