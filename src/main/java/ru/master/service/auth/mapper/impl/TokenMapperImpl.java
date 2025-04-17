package ru.master.service.auth.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.mapper.TokenMapper;
import ru.master.service.auth.model.dto.TokenDto;

@Component
public class TokenMapperImpl implements TokenMapper {


    @Override
    public TokenDto toDto(String accessToken) {

        return TokenDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
