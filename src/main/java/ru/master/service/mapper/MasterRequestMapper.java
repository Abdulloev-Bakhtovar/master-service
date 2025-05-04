package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterRequest;
import ru.master.service.model.dto.request.RejectMasterRequestReqDto;
import ru.master.service.model.dto.response.MasterRequestResDto;
import ru.master.service.model.dto.response.NewMasterRequestResDto;

public interface MasterRequestMapper {

    MasterRequest toMasterRequestEntity(User user, VerificationStatus verificationStatus);

    NewMasterRequestResDto toNewMasterRequestResDto(MasterProfile masterProfile, MasterRequest masterRequest);

    void toEntityForApprove(User admin, MasterRequest masterRequest);

    void toEntityForReject(User admin, MasterRequest masterRequest, RejectMasterRequestReqDto reqDto);

    MasterRequestResDto toMasterRequestResDto(MasterRequest masterRequest, User user, MasterProfile masterProfile);
}
