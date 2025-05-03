package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.mapper.MasterRequestMapper;
import ru.master.service.model.MasterRequest;

@Component
public class MasterRequestMapperImpl implements MasterRequestMapper {

    @Override
    public MasterRequest toMasterRequestEntity(User user, VerificationStatus verificationStatus) {
        if (user == null) return null;

        user.setVerificationStatus(verificationStatus);

        return MasterRequest.builder()
                .user(user)
                .build();
    }
}
