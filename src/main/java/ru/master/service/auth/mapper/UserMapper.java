package ru.master.service.auth.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.UserDto;

public interface UserMapper {

    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
