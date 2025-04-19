package ru.master.service.auth.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.mapper.TokenMapper;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.PhoneNumberDto;
import ru.master.service.auth.model.dto.TokenDto;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.*;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.constants.Role;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.util.AuthUtils;
import ru.master.service.util.CookieUtil;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final VerificationService verificationService;
    private final SmsService smsService;
    private final CookieUtil cookieUtil;
    private final JwtService jwtService;
    private final TokenMapper tokenMapper;
    private final TokenBlacklistService tokenBlacklistService;
    private final AuthUtils authUtils;

    @Override
    public void register(UserDto dto) {

        if (!userRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
            
            if (dto.getRole().equals(Role.ADMIN)) {
                throw new AppException(
                        ErrorMessage.ASSIGN_ADMIN_ROLE_FORBIDDEN,
                        HttpStatus.FORBIDDEN
                );
            }

            var user = userMapper.toEntity(dto);
            userRepo.save(user);
        }

        String code = verificationService.saveCode(dto.getPhoneNumber());
        smsService.sendVerificationCode(dto.getPhoneNumber(), code);
    }

    @Override
    public void login(PhoneNumberDto dto) {

        /*var user = userRepo.findByPhoneNumber(dto.getPhoneNumber())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        // TODO
        if (!user.isVerified()) {
            throw new AppException(
                    ErrorMessage.USER_NOT_VERIFIED,
                    HttpStatus.FORBIDDEN
            );
        }

        String code = verificationService.saveCode(dto.getPhoneNumber());
        smsService.sendVerificationCode(dto.getPhoneNumber(), code);*/
    }

    @Override
    public TokenDto refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = cookieUtil.extractRefreshTokenFromCookies(request);

        User user = validateRefreshTokenAndGetUser(refreshToken, response);

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        cookieUtil.addRefreshTokenToCookie(response, newRefreshToken);

        return tokenMapper.toDto(newAccessToken);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {

        addToBlacklist(request);
        cookieUtil.deleteRefreshTokenFromCookies(response);
    }

    @Override
    @Transactional
    public void delete(HttpServletRequest request, HttpServletResponse response) {

        var user = authUtils.getAuthenticatedUser();
        user = userRepo.findById(user.getId())
                        .orElseThrow(() -> new AppException(
                                ErrorMessage.USER_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        ));

        addToBlacklist(request);
        cookieUtil.deleteRefreshTokenFromCookies(response);
        userRepo.delete(user);
    }

    @Override
    public void updateVerificationStatus(User user, VerificationStatus verificationStatus) {
        user.setVerificationStatus(verificationStatus);
        userRepo.save(user); // TODO
    }

    private void addToBlacklist(HttpServletRequest request) {

        String refreshToken = cookieUtil.extractRefreshTokenFromCookies(request);

        if (refreshToken != null && jwtService.isTokenSignatureValid(refreshToken)
                && !jwtService.isTokenExpired(refreshToken)) {
            tokenBlacklistService.addToBlacklist(refreshToken);
        }

        Optional.ofNullable(request.getHeader("Authorization"))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(7))
                .filter(accessToken -> !jwtService.isTokenExpired(accessToken))
                .ifPresent(tokenBlacklistService::addToBlacklist);
    }


    private User validateRefreshTokenAndGetUser(String refreshToken, HttpServletResponse response) {
        if (refreshToken == null || refreshToken.isBlank()) {
            cookieUtil.deleteRefreshTokenFromCookies(response);
            throw new AppException(ErrorMessage.REFRESH_TOKEN_NOT_FOUND, HttpStatus.UNAUTHORIZED);
        }

        if (tokenBlacklistService.isBlacklisted(refreshToken)) {
            cookieUtil.deleteRefreshTokenFromCookies(response);
            throw new AppException(ErrorMessage.TOKEN_BLACKLISTED, HttpStatus.UNAUTHORIZED);
        }

        if (!jwtService.isTokenSignatureValid(refreshToken)) {
            cookieUtil.deleteRefreshTokenFromCookies(response);
            throw new AppException(ErrorMessage.INVALID_TOKEN_SIGNATURE, HttpStatus.UNAUTHORIZED);
        }

        if (!jwtService.isRefreshToken(refreshToken)) {
            cookieUtil.deleteRefreshTokenFromCookies(response);
            throw new AppException(ErrorMessage.INVALID_TOKEN_TYPE, HttpStatus.FORBIDDEN);
        }

        if (jwtService.isTokenExpired(refreshToken)) {
            cookieUtil.deleteRefreshTokenFromCookies(response);
            throw new AppException(ErrorMessage.TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
        }

        String userId = jwtService.extractUserId(refreshToken);

        return userRepo.findById(UUID.fromString(userId))
                .orElseThrow(() -> {
                    cookieUtil.deleteRefreshTokenFromCookies(response);
                    return new AppException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
    }
}
