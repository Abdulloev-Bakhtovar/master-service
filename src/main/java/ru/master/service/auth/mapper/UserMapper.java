package ru.master.service.auth.mapper;

import ru.master.service.auth.model.dto.request.RegisterAndLoginDto;
import ru.master.service.auth.model.User;
import ru.master.service.model.dto.UserForMasterRequestDto;

public interface UserMapper {

    User toEntity(RegisterAndLoginDto registerAndLoginDto);

    UserForMasterRequestDto toDto(User user);
}
