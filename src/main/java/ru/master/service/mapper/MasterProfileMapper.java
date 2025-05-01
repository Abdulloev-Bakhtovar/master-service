package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.*;
import ru.master.service.model.dto.MasterInfoForClientOrderDto;
import ru.master.service.model.dto.MasterProfileForCreateDto;
import ru.master.service.model.dto.MasterProfileForMasterRequestDto;
import ru.master.service.model.dto.request.CreateMasterProfileDto;
import ru.master.service.model.dto.response.NewMasterRequestDto;

import java.util.List;

public interface MasterProfileMapper {

    MasterInfoForClientOrderDto toOrderInfoForClientDto(MasterProfile masterProfile);

    MasterProfileForCreateDto toCreateProfileDto(CreateMasterProfileDto reqDto);

    MasterProfile toEntity(MasterProfileForCreateDto masterProfileDto, User user, City city);

    NewMasterRequestDto toNewMasterRequestDto(MasterProfile masterProfile, MasterRequest masterRequest);

    MasterProfileForMasterRequestDto toMasterRequestDto(MasterProfile masterProfile,
                                                        List<MasterSubService> masterSubServices,
                                                        UserAgreement userAgreement);
}
