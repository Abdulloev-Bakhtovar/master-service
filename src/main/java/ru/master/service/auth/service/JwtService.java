package ru.master.service.auth.service;

import org.springframework.security.core.GrantedAuthority;
import ru.master.service.auth.model.User;

import java.time.Instant;
import java.util.Collection;

public interface JwtService {

    String extractPhoneNumber(String jwt);

    String extractUserId(String jwt);

    String extractTokenType(String jwt);

    Instant extractExpiration(String token);

    Collection<? extends GrantedAuthority> extractAuthorities(String jwt);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    boolean isTokenExpired(String token);

    boolean isTokenSignatureValid(String refreshToken);

    boolean isRefreshToken(String token);

    boolean isAccessToken(String token);
}
