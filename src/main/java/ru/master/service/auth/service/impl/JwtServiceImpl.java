package ru.master.service.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.master.service.auth.model.TokenClaims;
import ru.master.service.auth.model.User;
import ru.master.service.auth.service.JwtService;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.util.KeyProviderUtil;

import java.nio.file.NoSuchFileException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${application.security.jwt.access-token.expiration}")
    private int accessTokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private int refreshTokenExpiration;

    @Value("${application.security.jwt.keys.public}")
    private String publicKeyFromFile;

    @Value("${application.security.jwt.keys.private}")
    private String privateKeyFromFile;

    private static final String TOKEN_TYPE_ACCESS = "ACCESS";
    private static final String TOKEN_TYPE_REFRESH = "REFRESH";

    @Override
    public String extractPhoneNumber(String jwt) {
        return extractClaim(jwt, claims -> claims.get("phoneNumber", String.class));
    }

    @Override
    public String extractUserId(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    @Override
    public String extractTokenType(String jwt) {
        return extractClaim(jwt, claims -> claims.get("tokenType", String.class));
    }

    @Override
    public Instant extractExpiration(String token) {
        return extractClaim(token, claims ->
                claims.getExpiration().toInstant());
    }

    @Override
    public Collection<? extends GrantedAuthority> extractAuthorities(String jwt) {
        List<?> roles = extractClaim(jwt, claims -> claims.get("roles", List.class));

        if (roles == null || roles.isEmpty()) {
            throw new AppException(ErrorMessage.MISSING_JWT_ROLES, HttpStatus.BAD_REQUEST);
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .toList();
    }

    @Override
    public String generateAccessToken(User user) {
        TokenClaims claims = new TokenClaims(
                user.getPhoneNumber(),
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList(),
                TOKEN_TYPE_ACCESS
        );
        return generateToken(user, accessTokenExpiration, claims.toMap());
    }

    @Override
    public String generateRefreshToken(User user) {
        TokenClaims claims = new TokenClaims(
                user.getPhoneNumber(),
                null,
                TOKEN_TYPE_REFRESH
        );
        return generateToken(user, refreshTokenExpiration, claims.toMap());
    }

    private String generateToken(User user,
                                 long expirationMinutes,
                                 Map<String, Object> customClaims) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiration = Date.from(now.plus(Duration.ofMinutes(expirationMinutes)));

        try {
            PrivateKey privateKey = KeyProviderUtil.getPrivateKey(this.privateKeyFromFile);

            return Jwts.builder()
                    .subject(String.valueOf(user.getId()))
                    .claims(customClaims)
                    .issuedAt(issuedAt)
                    .expiration(expiration)
                    .signWith(privateKey)
                    .compact();
        } catch (NoSuchFileException ex) {
            throw new AppException(ErrorMessage.PRIVATE_KEY_FILE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new AppException(ErrorMessage.JWT_SIGNING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).isBefore(Instant.now());
        } catch (AppException e) {
            if (e.getMessage().equals(ErrorMessage.TOKEN_EXPIRED)) {
                return true;
            }
            throw e;
        }
    }

    @Override
    public boolean isTokenSignatureValid(String token) {
        try {
            PublicKey publicKey = KeyProviderUtil.getPublicKey(this.publicKeyFromFile);
            Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new AppException(ErrorMessage.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isRefreshToken(String token) {
        return TOKEN_TYPE_REFRESH.equals(extractTokenType(token));
    }

    @Override
    public boolean isAccessToken(String token) {
        return TOKEN_TYPE_ACCESS.equals(extractTokenType(token));
    }

    private Claims extractAllClaims(String token) {
        try {
            PublicKey publicKey = KeyProviderUtil.getPublicKey(this.publicKeyFromFile);
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (NoSuchFileException e) {
            throw new AppException(ErrorMessage.PUBLIC_KEY_FILE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (ExpiredJwtException e) {
            throw new AppException(ErrorMessage.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new AppException(ErrorMessage.JWT_PARSING_ERROR, HttpStatus.UNAUTHORIZED);
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
