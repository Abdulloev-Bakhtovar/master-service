package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.mapper.MasterApplicationMapper;
import ru.master.service.model.MasterApplication;

@Component
public class MasterApplicationMapperImpl implements MasterApplicationMapper {

    @Override
    public MasterApplication toEntity(User user, VerificationStatus verificationStatus) {
        if (user == null) return null;

        user.setVerificationStatus(verificationStatus);

        return MasterApplication.builder()
                .user(user)
                .build();
    }

    @Override
    public void toEntity(User admin,
                         User user,
                         VerificationStatus verificationStatus,
                         MasterApplication existsApplication,
                         String rejectionReason) {

        user.setVerificationStatus(verificationStatus);
        existsApplication.setUser(user);
        existsApplication.setReviewedByAdminId(admin.getId());

        if (rejectionReason != null && !rejectionReason.isEmpty()) {
            existsApplication.setRejectionReason(rejectionReason);
        }
    }
}
