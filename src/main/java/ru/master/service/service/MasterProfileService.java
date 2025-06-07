package ru.master.service.service;

import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;
import ru.master.service.model.dto.request.MasterStatusUpdateDto;
import ru.master.service.model.dto.response.AllMastersResDto;
import ru.master.service.model.dto.response.MasterInfoForProfileResDto;
import ru.master.service.model.dto.response.MasterProfileResDto;

import java.util.List;
import java.util.UUID;

public interface MasterProfileService {

    List<AllMastersResDto> getAll();

    MasterProfileResDto getById(UUID id);

    void create(CreateMasterProfileReqDto reqDto) throws Exception;

    void updateMasterAverageRating(MasterProfile masterProfile, Float rating);

    void updateMasterStatus(MasterStatusUpdateDto reqDto);

    EnumResDto getMasterStatus();

    MasterInfoForProfileResDto getMasterInfo();
}
