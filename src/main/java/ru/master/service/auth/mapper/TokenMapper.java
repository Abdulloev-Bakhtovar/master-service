package ru.master.service.auth.mapper;

import ru.master.service.auth.model.dto.response.TokenResDto;

public interface TokenMapper {

    TokenResDto toDto(String accessToken, String refreshToken);
}
