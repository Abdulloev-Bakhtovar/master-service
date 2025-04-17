package ru.master.service.mapper;

import ru.master.service.model.User;
import ru.master.service.model.dto.request.UserDto;

public interface UserMapper {

    User toEntity(UserDto userDto);
}
