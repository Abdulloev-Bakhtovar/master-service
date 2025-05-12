package ru.master.service.auth.service;

import ru.master.service.auth.model.dto.response.TokenDto;

public interface TokenBlacklistService {

    void addToBlacklist(TokenDto tokenDto);

    boolean isBlacklisted(String token);
}
