package ru.master.service.auth.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.request.RegisterAndLoginDto;

public interface UserMapper {

    User toEntity(RegisterAndLoginDto registerAndLoginDto);
}
