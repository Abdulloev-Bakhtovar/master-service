package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.mapper.MasterRequestMapper;
import ru.master.service.model.MasterRequest;
import ru.master.service.model.dto.MasterProfileForMasterRequestDto;
import ru.master.service.model.dto.UserForMasterRequestDto;
import ru.master.service.model.dto.response.MasterRequestDto;

@Component
public class MasterRequestMapperImpl implements MasterRequestMapper {

    @Override
    public MasterRequest toEntity(User user, VerificationStatus verificationStatus) {
        if (user == null) return null;

        user.setVerificationStatus(verificationStatus);

        return MasterRequest.builder()
                .user(user)
                .build();
    }

    @Override
    public MasterRequestDto toMasterRequestDto(MasterRequest entity,
                                               MasterProfileForMasterRequestDto masterProfileDto,
                                               UserForMasterRequestDto userDto) {
        if (entity == null) return null;

        return MasterRequestDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .rejectionReason(entity.getRejectionReason())
                .reviewedByAdminId(entity.getReviewedByAdminId())
                .userDto(userDto)
                .masterProfileDto(masterProfileDto)
                .build();
    }

    @Override
    public void toEntityWithAdmin(User admin,
                                  MasterRequest entity,
                                  VerificationStatus verificationStatus,
                                  String rejectionReason
    ) {
        entity.getUser().setVerificationStatus(verificationStatus);
        entity.setReviewedByAdminId(admin.getId());

        if (rejectionReason != null) {
            entity.setRejectionReason(rejectionReason);
        }
    }
}
