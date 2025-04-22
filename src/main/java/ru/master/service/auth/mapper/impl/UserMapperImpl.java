package ru.master.service.auth.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.constants.VerificationStatus;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto dto) {

        if(dto == null) return null;

        return User.builder()
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .verificationStatus(VerificationStatus.PHONE_NOT_VERIFIED)
                .build();
    }

    @Override
    public UserDto toDto(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .id(user.getId())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .verificationStatus(user.getVerificationStatus())
                .build();
    }
}
