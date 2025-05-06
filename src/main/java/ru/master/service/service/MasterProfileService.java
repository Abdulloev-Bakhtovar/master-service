package ru.master.service.service;

import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;
import ru.master.service.model.dto.request.MasterStatusUpdateDto;
import ru.master.service.model.dto.response.MasterInfoForProfileResDto;

public interface MasterProfileService {

    void create(CreateMasterProfileReqDto reqDto) throws Exception;

    void updateMasterAverageRating(MasterProfile masterProfile, Float rating);

    void updateMasterStatus(MasterStatusUpdateDto reqDto);

    EnumResDto getMasterStatus();

    MasterInfoForProfileResDto getMasterInfo();
}
