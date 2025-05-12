package ru.master.service.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.auth.service.JwtService;
import ru.master.service.auth.service.TokenBlacklistService;
import ru.master.service.exception.AppException;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final JwtService jwtService;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_PREFIX = "blacklist:";
    private static final int SAFETY_BUFFER_SECONDS = 10;

    @Override
    public void addToBlacklist(TokenDto tokenDto) {
        // Проверяем и добавляем refresh token
        Optional.ofNullable(tokenDto.getRefreshToken())
                .filter(jwtService::isTokenSignatureValid)
                .filter(refreshToken -> !jwtService.isTokenExpired(refreshToken))
                .ifPresent(this::addToBlacklist);

        // Проверяем и добавляем access token
        Optional.ofNullable(tokenDto.getAccessToken())
                .filter(accessToken -> !jwtService.isTokenExpired(accessToken))
                .ifPresent(this::addToBlacklist);
    }

    private void addToBlacklist(String token) {
        try {
            Instant expiration = jwtService.extractExpiration(token);
            Instant now = Instant.now();

            if (expiration.isAfter(now)) {
                long ttl = Duration.between(now, expiration).getSeconds() + SAFETY_BUFFER_SECONDS;

                DateTimeFormatter formatter = DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault());

                String deleteAt = formatter.format(now.plusSeconds(ttl));
                String value = String.format("Token revoked. Will be deleted at: %s", deleteAt);

                redisTemplate.opsForValue().set(
                        BLACKLIST_PREFIX + token,
                        value,
                        ttl,
                        TimeUnit.SECONDS
                );
            }
        } catch (Exception e) {
            throw new AppException("Failed to blacklist token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token));
    }
}
