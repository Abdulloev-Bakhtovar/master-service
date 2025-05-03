package ru.master.service.auth.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.dto.response.TokenResDto;
import ru.master.service.auth.mapper.TokenMapper;

@Component
public class TokenMapperImpl implements TokenMapper {

    @Override
    public TokenResDto toDto(String accessToken, String refreshToken) {

        return TokenResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
