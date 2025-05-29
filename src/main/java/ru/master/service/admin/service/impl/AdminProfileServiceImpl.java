package ru.master.service.admin.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.admin.model.AdminProfile;
import ru.master.service.admin.model.dto.EmailDto;
import ru.master.service.admin.model.dto.request.CreateAdminProfileReqDto;
import ru.master.service.admin.model.dto.request.LoginAdminProfileReqDto;
import ru.master.service.admin.model.dto.request.ResetPasswordReqDto;
import ru.master.service.admin.repository.AdminProfileRepo;
import ru.master.service.admin.service.AdminProfileService;
import ru.master.service.admin.service.EmailService;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.auth.service.JwtService;
import ru.master.service.auth.service.TokenBlacklistService;
import ru.master.service.auth.service.VerificationService;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.Role;
import ru.master.service.exception.AppException;
import ru.master.service.util.AuthUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminProfileServiceImpl implements AdminProfileService {

    private final AdminProfileRepo adminProfileRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final VerificationService verificationService;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public void create(CreateAdminProfileReqDto reqDto) {

        if (adminProfileRepo.existsByEmail(reqDto.getEmail())) {
            throw new AppException(
                    ErrorMessage.ADMIN_PROFILE_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        var adminProfile = AdminProfile.builder()
                .name(reqDto.getName())
                .email(reqDto.getEmail())
                .role(Role.ADMIN)
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .build();

        adminProfileRepo.save(adminProfile);
    }

    @Override
    public TokenDto login(LoginAdminProfileReqDto reqDto) {

        var admin = adminProfileRepo.findByEmail(reqDto.getEmail())
                        .orElseThrow(() -> new AppException(
                                ErrorMessage.USER_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        ));

        AuthUtil.validateCredentials(reqDto.getEmail(), reqDto.getPassword());

        var accessToken = jwtService.generateAdminAccessToken(admin);
        var refreshToken = jwtService.generateAdminRefreshToken(admin);

        return new TokenDto(accessToken, refreshToken);
    }

    @Override
    public void logout(TokenDto tokenDto) {
        tokenBlacklistService.addToBlacklist(tokenDto);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return adminProfileRepo.findByEmail(email)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
    }

    @Override
    public void resetPasswordRequest(EmailDto reqDto) throws MessagingException {

        var admin = adminProfileRepo.findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        emailService.sendEmail(admin.getEmail());
    }

    @Override
    public void confirmTokenFromEmailAndResetPass(ResetPasswordReqDto reqDto) {

        var admin = adminProfileRepo.findByEmail(reqDto.getEmail())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        if (verificationService.isValidCodeForResetPass(reqDto)) {
            admin.setPassword(passwordEncoder.encode(reqDto.getNewPassword()));
            adminProfileRepo.save(admin);
        }
    }
}
