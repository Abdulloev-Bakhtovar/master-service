package ru.master.service.auth.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.Role;
import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.UserDto;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDto userDto, Role role) {

        if(userDto == null) return null;

        return User.builder()
                .phoneNumber(userDto.getPhoneNumber())
                .roles(Collections.singletonList(role))
                .isVerified(false)
                .build();
    }
}
