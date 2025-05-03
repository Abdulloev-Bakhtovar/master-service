package ru.master.service.auth.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.auth.model.dto.request.RegisterOrLoginReqDto;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.model.dto.UserForMasterRequestDto;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(RegisterOrLoginReqDto dto) {
        if(dto == null) return null;

        return User.builder()
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .verificationStatus(VerificationStatus.PHONE_NOT_VERIFIED)
                .build();
    }

    @Override
    public UserForMasterRequestDto toDto(User entity) {
        if(entity == null) return null;

        return UserForMasterRequestDto.builder()
                .id(entity.getId())
                .phoneNumber(entity.getPhoneNumber())
                .role(entity.getRole())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .verificationStatus(entity.getVerificationStatus())
                .build();
    }
}
