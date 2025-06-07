package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.Subservice;
import ru.master.service.model.dto.MasterProfileForCreateDto;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;
import ru.master.service.model.dto.response.AllMastersResDto;
import ru.master.service.model.dto.response.MasterInfoForProfileResDto;
import ru.master.service.model.dto.response.MasterProfileResDto;

import java.util.List;

public interface MasterProfileMapper {

    /** For createForOrder master profile */
    MasterProfileForCreateDto toMasterProfileForCreateDto(CreateMasterProfileReqDto reqDto);

    MasterProfile toMasterProfileEntity(MasterProfileForCreateDto masterProfileDto,
                                        User user,
                                        City city,
                                        List<Subservice> subservices
    );

    MasterInfoForProfileResDto toMasterInfoForProfileResDto(MasterProfile master);

    AllMastersResDto toAllMastersResDto(MasterProfile masterProfile, int completedOrderCount);

    MasterProfileResDto toMasterProfileResDto(MasterProfile masterProfile,
                                              int completedOrdersThisMonth,
                                              int totalCompletedOrders
    );
}
