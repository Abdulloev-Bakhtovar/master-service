package ru.master.service.auth.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.auth.mapper.TokenMapper;

@Component
public class TokenMapperImpl implements TokenMapper {

    @Override
    public TokenDto toDto(String accessToken, String refreshToken) {

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
