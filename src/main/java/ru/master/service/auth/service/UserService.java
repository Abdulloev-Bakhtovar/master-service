package ru.master.service.auth.service;

import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.auth.model.dto.PhoneNumberDto;

public interface UserService {

    void register(UserDto userDto);

    void login(PhoneNumberDto loginDto);
}
