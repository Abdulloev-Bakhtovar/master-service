package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.mapper.MasterRequestMapper;
import ru.master.service.model.MasterRequest;
import ru.master.service.model.dto.inner.MasterProfileForCreateDto;
import ru.master.service.model.dto.MasterRequestDto;

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
    public void toEntity(User admin,
                         User user,
                         VerificationStatus verificationStatus,
                         MasterRequest existsApplication,
                         String rejectionReason) {

        user.setVerificationStatus(verificationStatus);
        existsApplication.setUser(user);
        existsApplication.setReviewedByAdminId(admin.getId());

        if (rejectionReason != null && !rejectionReason.isEmpty()) {
            existsApplication.setRejectionReason(rejectionReason);
        }
    }

    @Override
    public MasterRequestDto toDto(MasterRequest entity, MasterProfileForCreateDto masterProfileForCreateDto, UserDto userDto) {
        if (entity == null) return null;

        return MasterRequestDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .rejectionReason(entity.getRejectionReason())
                .reviewedByAdminId(entity.getReviewedByAdminId())
                .userDto(userDto)
                .masterProfileForCreateDto(masterProfileForCreateDto)
                .build();
    }

    @Override
    public void toEntity(User admin, MasterRequest masterRequest, VerificationStatus rejected, String rejectionReason) {
        masterRequest.getUser().setVerificationStatus(rejected);
        masterRequest.setReviewedByAdminId(admin.getId());

        if (rejectionReason != null && !rejectionReason.isEmpty()) {
            masterRequest.setRejectionReason(rejectionReason);
        }
    }
}
