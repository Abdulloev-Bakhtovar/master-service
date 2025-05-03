package ru.master.service.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.auth.model.dto.request.RefreshTokenReqDto;
import ru.master.service.auth.model.dto.request.RegisterOrLoginReqDto;
import ru.master.service.auth.model.dto.response.TokenResDto;
import ru.master.service.auth.mapper.TokenMapper;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.*;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.Role;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.util.AuthUtil;
import ru.master.service.util.CookieUtil;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final VerificationService verificationService;
    private final SmsService smsService;
    private final CookieUtil cookieUtil;
    private final JwtService jwtService;
    private final TokenMapper tokenMapper;
    private final TokenBlacklistService tokenBlacklistService;
    private final AuthUtil authUtil;

    @Override
    public void registerOrLogin(RegisterOrLoginReqDto registerOrLoginReqDto) {

        if (!userRepo.existsByPhoneNumber(registerOrLoginReqDto.getPhoneNumber())) {
            
            if (registerOrLoginReqDto.getRole().equals(Role.ADMIN)) {
                throw new AppException(
                        ErrorMessage.ASSIGN_ADMIN_ROLE_FORBIDDEN,
                        HttpStatus.FORBIDDEN
                );
            }

            var user = userMapper.toEntity(registerOrLoginReqDto);
            userRepo.save(user);
        }

        String code = verificationService.saveCode(registerOrLoginReqDto.getPhoneNumber());
        smsService.sendVerificationCode(registerOrLoginReqDto.getPhoneNumber(), code);
    }

    @Override
    public TokenResDto refreshToken(RefreshTokenReqDto refreshTokenReqDto) {

        User user = validateRefreshTokenAndGetUser(refreshTokenReqDto);

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return tokenMapper.toDto(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(TokenResDto tokenResDto) {
        addToBlacklist(tokenResDto);
    }

    @Override
    public void delete(TokenResDto tokenResDto) {

        var user = authUtil.getAuthenticatedUser();
        user = userRepo.findById(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        addToBlacklist(tokenResDto);
        userRepo.delete(user);
    }

    @Override
    public void updateVerificationStatus(User user, VerificationStatus verificationStatus) {
        user.setVerificationStatus(verificationStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public EnumResDto getVerificationStatusByPhoneNumber(String phoneNumber) {
        var user = userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return EnumResDto.builder()
                .name(user.getVerificationStatus().name())
                .displayName(user.getVerificationStatus().getDisplayName())
                .build();
    }

    private void addToBlacklist(TokenResDto tokenResDto) {
        // Проверяем и добавляем refresh token
        Optional.ofNullable(tokenResDto.getRefreshToken())
                .filter(jwtService::isTokenSignatureValid)
                .filter(refreshToken -> !jwtService.isTokenExpired(refreshToken))
                .ifPresent(tokenBlacklistService::addToBlacklist);

        // Проверяем и добавляем access token
        Optional.ofNullable(tokenResDto.getAccessToken())
                .filter(accessToken -> !jwtService.isTokenExpired(accessToken))
                .ifPresent(tokenBlacklistService::addToBlacklist);
    }


    private User validateRefreshTokenAndGetUser(RefreshTokenReqDto refreshTokenReqDto) {


        if (!jwtService.isTokenSignatureValid(refreshTokenReqDto.getToken())) {
            throw new AppException(
                    ErrorMessage.INVALID_TOKEN_SIGNATURE,
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (!jwtService.isRefreshToken(refreshTokenReqDto.getToken())) {
            throw new AppException(
                    ErrorMessage.INVALID_TOKEN_TYPE,
                    HttpStatus.FORBIDDEN
            );
        }

        if (jwtService.isTokenExpired(refreshTokenReqDto.getToken())) {
            throw new AppException(
                    ErrorMessage.TOKEN_EXPIRED,
                    HttpStatus.UNAUTHORIZED
            );
        }

        UUID userId = UUID.fromString(jwtService.extractUserId(refreshTokenReqDto.getToken()));

        return userRepo.findById(userId)
                .orElseThrow(() -> new AppException(
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                ));
    }
}
