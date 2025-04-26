package ru.master.service.auth.service;

import ru.master.service.model.dto.EnumDto;

public interface UserService {

    EnumDto getVerificationStatusByPhoneNumber(String phoneNumber);
}
