package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.model.MasterRequest;
import ru.master.service.model.dto.inner.MasterProfileForCreateDto;
import ru.master.service.model.dto.MasterRequestDto;

public interface MasterRequestMapper {

    MasterRequest toEntity(User user, VerificationStatus verificationStatus);

    void toEntity(User admin,
                  User user,
                  VerificationStatus verificationStatus,
                  MasterRequest existsApplication,
                  String rejectionReason);

    MasterRequestDto toDto(MasterRequest entity, MasterProfileForCreateDto masterProfileForCreateDto, UserDto userDto);

    void toEntity(User admin, MasterRequest masterRequest, VerificationStatus rejected, String rejectionReason);
}
