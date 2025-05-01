package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.model.MasterRequest;
import ru.master.service.model.dto.MasterProfileForMasterRequestDto;
import ru.master.service.model.dto.UserForMasterRequestDto;
import ru.master.service.model.dto.response.MasterRequestDto;

public interface MasterRequestMapper {

    MasterRequest toEntity(User user, VerificationStatus verificationStatus);

    MasterRequestDto toMasterRequestDto(MasterRequest masterRequest,
                                        MasterProfileForMasterRequestDto masterProfileDto,
                                        UserForMasterRequestDto userDto);

    void toEntityWithAdmin(User admin,
                           MasterRequest masterRequest,
                           VerificationStatus verificationStatus,
                           String rejectionReason);
}
