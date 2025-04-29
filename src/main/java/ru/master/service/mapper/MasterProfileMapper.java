package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.*;
import ru.master.service.model.dto.NewMasterRequestDto;
import ru.master.service.model.dto.request.CreateMasterProfileDto;
import ru.master.service.model.dto.inner.MasterProfileForCreateDto;

import java.util.List;

public interface MasterProfileMapper {

    MasterProfile toEntity(MasterProfileForCreateDto dto, User user, City city);

    MasterProfileForCreateDto toDto(MasterProfile entity,
                                    List<MasterSubService> masterSubServices,
                                    UserAgreement userAgreement);

    MasterProfileForCreateDto toMasterProfileDto(CreateMasterProfileDto request);

    NewMasterRequestDto toDto(MasterProfile masterProfile, MasterRequest masterRequest);
}
