package ru.master.service.auth.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.request.RegisterAndLoginDto;
import ru.master.service.constant.VerificationStatus;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RegisterAndLoginDto dto) {
        if(dto == null) return null;

        return User.builder()
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .verificationStatus(VerificationStatus.PHONE_NOT_VERIFIED)
                .build();
    }
}
