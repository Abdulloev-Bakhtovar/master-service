package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.UserMapper;
import ru.master.service.model.User;
import ru.master.service.model.dto.request.UserDto;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto userDto) {

        if(userDto == null) return null;

        return User.builder()
                .phoneNumber(userDto.getPhoneNumber())
                .role(userDto.getRole())
                .isVerified(false)
                .build();
    }
}
