package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.Subservice;
import ru.master.service.model.dto.MasterProfileForCreateDto;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;

import java.util.List;

public interface MasterProfileMapper {

    /** For create master profile */
    MasterProfileForCreateDto toMasterProfileForCreateDto(CreateMasterProfileReqDto reqDto);

    MasterProfile toMasterProfileEntity(MasterProfileForCreateDto masterProfileDto,
                                        User user,
                                        City city,
                                        List<Subservice> subservices);
}
