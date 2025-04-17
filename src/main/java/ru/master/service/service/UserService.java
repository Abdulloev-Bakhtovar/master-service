package ru.master.service.service;

import ru.master.service.model.dto.request.UserDto;

public interface UserService {

    void register(UserDto userDto);
}
