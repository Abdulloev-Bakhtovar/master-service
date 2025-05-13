package ru.master.service.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.mapper.TokenMapper;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.request.RefreshTokenDto;
import ru.master.service.auth.model.dto.request.RegisterAndLoginDto;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.*;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.Role;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.repository.ClientProfileRepo;
import ru.master.service.util.AuthUtil;

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
    private final JwtService jwtService;
    private final TokenMapper tokenMapper;
    private final TokenBlacklistService tokenBlacklistService;
    private final AuthUtil authUtil;
    private final ClientProfileRepo clientProfileRepo;

    @Override
    public void registerOrLogin(RegisterAndLoginDto dto) {
        Optional<User> userOpt = userRepo.findByPhoneNumber(dto.getPhoneNumber());

        if (userOpt.isEmpty()) {
            if (dto.getRole() == Role.ADMIN) {
                throw new AppException(
                        ErrorMessage.ASSIGN_ADMIN_ROLE_FORBIDDEN,
                        HttpStatus.FORBIDDEN
                );
            }

            User newUser = userMapper.toEntity(dto);
            userRepo.save(newUser);

            if (dto.getRole() == Role.CLIENT &&
                    dto.getReferralCode() != null &&
                    !dto.getReferralCode().isBlank()) {

                if (!clientProfileRepo.existsByReferralCode(dto.getReferralCode())) {
                    throw new AppException(
                            "Referral code not found",
                            HttpStatus.NOT_FOUND
                    );
                }

                verificationService.addReferralCodeToCache(newUser.getId(), dto.getReferralCode());
            }
        } else {
            User user = userOpt.get();

            if (user.getVerificationStatus() == VerificationStatus.REJECTED) {
                throw new AppException(
                        ErrorMessage.USER_STATUS_BLOCKED_OR_INVALID,
                        HttpStatus.FORBIDDEN
                );
            }
        }

        String code = verificationService.saveCode(dto.getPhoneNumber());
        smsService.sendVerificationCode(dto.getPhoneNumber(), code);
    }

    @Override
    public TokenDto refreshToken(RefreshTokenDto refreshTokenDto) {

        // TODO добавить старые токены в черный список
        User user = validateRefreshTokenAndGetUser(refreshTokenDto);

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return tokenMapper.toDto(newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(TokenDto tokenDto) {
        tokenBlacklistService.addToBlacklist(tokenDto);
    }

    @Override
    public void delete(TokenDto tokenDto) {

        var user = authUtil.getAuthenticatedUser();
        user = userRepo.findById(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        tokenBlacklistService.addToBlacklist(tokenDto);
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

        UUID userId = UUID.fromString(jwtService.extractId(refreshTokenDto.getToken()));

        return userRepo.findById(userId)
                .orElseThrow(() -> new AppException(
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                ));
    }
}
