package ru.master.service.auth.mapper;

import ru.master.service.auth.model.dto.TokenDto;

public interface TokenMapper {

    TokenDto toDto(String accessToken);
}
