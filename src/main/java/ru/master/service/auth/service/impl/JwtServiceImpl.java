package ru.master.service.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.master.service.auth.model.TokenClaim;
import ru.master.service.auth.model.User;
import ru.master.service.auth.service.JwtService;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.Role;
import ru.master.service.exception.AppException;
import ru.master.service.model.AdminProfile;
import ru.master.service.util.KeyProviderUtil;

import java.nio.file.NoSuchFileException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
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
    public String extractEmail(String jwt) {
        return extractClaim(jwt, claims -> claims.get("email", String.class));
    }

    @Override
    public Role extractRole(String jwt) {
        String roleStr = extractClaim(jwt, claims -> claims.get("role", String.class));
        try {
            return Role.valueOf(roleStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new AppException(
                    ErrorMessage.INVALID_JWT_ROLE,
                    HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public String extractId(String jwt) {
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
        Role role = extractRole(jwt);

        if (role == null) {
            throw new AppException(
                    ErrorMessage.MISSING_JWT_ROLES,
                    HttpStatus.UNAUTHORIZED
            );
        }

        return Collections.singletonList(
                new SimpleGrantedAuthority(role.name())
        );
    }

    @Override
    public String generateAccessToken(User user) {
        TokenClaim claims = new TokenClaim(
                user.getPhoneNumber(),
                user.getRole(),
                TOKEN_TYPE_ACCESS,
                null
        );
        return generateToken(user.getId(), accessTokenExpiration, claims.toMap());
    }

    @Override
    public String generateRefreshToken(User user) {
        TokenClaim claims = new TokenClaim(
                user.getPhoneNumber(),
                null,
                TOKEN_TYPE_REFRESH,
                null
        );
        return generateToken(user.getId(), refreshTokenExpiration, claims.toMap());
    }

    @Override
    public String generateAdminAccessToken(AdminProfile admin) {
        TokenClaim claims = new TokenClaim(
                null,
                admin.getRole(),
                TOKEN_TYPE_ACCESS,
                admin.getEmail()
        );
        return generateToken(admin.getId(), accessTokenExpiration, claims.toMap());
    }

    @Override
    public String generateAdminRefreshToken(AdminProfile admin) {
        TokenClaim claims = new TokenClaim(
                null,
                null,
                TOKEN_TYPE_REFRESH,
                admin.getEmail()
        );
        return generateToken(admin.getId(), refreshTokenExpiration, claims.toMap());
    }

    private String generateToken(UUID sub,
                                 long expirationMinutes,
                                 Map<String, Object> customClaims) {
        Instant now = Instant.now();
        Date issuedAt = Date.from(now);
        Date expiration = Date.from(now.plus(Duration.ofMinutes(expirationMinutes)));

        try {
            PrivateKey privateKey = KeyProviderUtil.getPrivateKey(this.privateKeyFromFile);

            return Jwts.builder()
                    .subject(String.valueOf(sub))
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
