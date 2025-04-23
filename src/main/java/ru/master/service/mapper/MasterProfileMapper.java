package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.UserAgreement;
import ru.master.service.model.dto.NewMasterRequestDto;
import ru.master.service.model.dto.MasterProfileCreateDto;
import ru.master.service.model.dto.MasterProfileDto;

import java.util.List;

public interface MasterProfileMapper {

    MasterProfile toEntity(MasterProfileDto dto, User user, City city);

    MasterProfileDto toDto(MasterProfile entity,
                           List<MasterSubService> masterSubServices,
                           UserAgreement userAgreement);

    MasterProfileDto toDto(MasterProfileCreateDto request);

    NewMasterRequestDto toDto(MasterProfile masterProfile);
}
