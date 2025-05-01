package ru.master.service.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.model.dto.response.EnumDto;
import ru.master.service.auth.model.dto.request.RefreshTokenDto;
import ru.master.service.auth.model.dto.request.RegisterAndLoginDto;
import ru.master.service.auth.model.dto.response.TokenDto;
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
    public void registerOrLogin(RegisterAndLoginDto registerAndLoginDto) {

        if (!userRepo.existsByPhoneNumber(registerAndLoginDto.getPhoneNumber())) {
            
            if (registerAndLoginDto.getRole().equals(Role.ADMIN)) {
                throw new AppException(
                        ErrorMessage.ASSIGN_ADMIN_ROLE_FORBIDDEN,
                        HttpStatus.FORBIDDEN
                );
            }

            var user = userMapper.toEntity(registerAndLoginDto);
            userRepo.save(user);
        }

        String code = verificationService.saveCode(registerAndLoginDto.getPhoneNumber());
        smsService.sendVerificationCode(registerAndLoginDto.getPhoneNumber(), code);
    }

    @Override
    public TokenDto refreshToken(RefreshTokenDto refreshTokenDto) {

        User user = validateRefreshTokenAndGetUser(refreshTokenDto);

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return tokenMapper.toDto(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(TokenDto tokenDto) {
        addToBlacklist(tokenDto);
    }

    @Override
    public void delete(TokenDto tokenDto) {

        var user = authUtil.getAuthenticatedUser();
        user = userRepo.findById(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        addToBlacklist(tokenDto);
        userRepo.delete(user);
    }

    @Override
    public void updateVerificationStatus(User user, VerificationStatus verificationStatus) {
        user.setVerificationStatus(verificationStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public EnumDto getVerificationStatusByPhoneNumber(String phoneNumber) {
        var user = userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return EnumDto.builder()
                .name(user.getVerificationStatus().name())
                .displayName(user.getVerificationStatus().getDisplayName())
                .build();
    }

    private void addToBlacklist(TokenDto tokenDto) {
        // Проверяем и добавляем refresh token
        Optional.ofNullable(tokenDto.getRefreshToken())
                .filter(jwtService::isTokenSignatureValid)
                .filter(refreshToken -> !jwtService.isTokenExpired(refreshToken))
                .ifPresent(tokenBlacklistService::addToBlacklist);

        // Проверяем и добавляем access token
        Optional.ofNullable(tokenDto.getAccessToken())
                .filter(accessToken -> !jwtService.isTokenExpired(accessToken))
                .ifPresent(tokenBlacklistService::addToBlacklist);
    }


    private User validateRefreshTokenAndGetUser(RefreshTokenDto refreshTokenDto) {


        if (!jwtService.isTokenSignatureValid(refreshTokenDto.getToken())) {
            throw new AppException(
                    ErrorMessage.INVALID_TOKEN_SIGNATURE,
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (!jwtService.isRefreshToken(refreshTokenDto.getToken())) {
            throw new AppException(
                    ErrorMessage.INVALID_TOKEN_TYPE,
                    HttpStatus.FORBIDDEN
            );
        }

        if (jwtService.isTokenExpired(refreshTokenDto.getToken())) {
            throw new AppException(
                    ErrorMessage.TOKEN_EXPIRED,
                    HttpStatus.UNAUTHORIZED
            );
        }

        UUID userId = UUID.fromString(jwtService.extractUserId(refreshTokenDto.getToken()));

        return userRepo.findById(userId)
                .orElseThrow(() -> new AppException(
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                ));
    }
}
