package ru.master.service.auth.mapper;

import ru.master.service.auth.model.dto.request.RegisterOrLoginReqDto;
import ru.master.service.auth.model.User;
import ru.master.service.model.dto.UserForMasterRequestDto;

public interface UserMapper {

    User toEntity(RegisterOrLoginReqDto registerOrLoginReqDto);

    UserForMasterRequestDto toDto(User user);
}
