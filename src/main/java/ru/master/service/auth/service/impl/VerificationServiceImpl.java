package ru.master.service.auth.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.model.dto.request.SmsVerificationReqDto;
import ru.master.service.auth.model.dto.request.PhoneNumberReqDto;
import ru.master.service.auth.model.dto.response.TokenResDto;
import ru.master.service.auth.mapper.TokenMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.JwtService;
import ru.master.service.auth.service.SmsService;
import ru.master.service.auth.service.VerificationService;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.util.CodeGeneratorUtil;
import ru.master.service.util.CookieUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final SmsService smsService;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final CookieUtil cookieUtil;
    private final TokenMapper tokenMapper;

    @Value("${application.sms-verification.prefix}")
    private String prefix;

    @Value("${application.sms-verification.ttl-minutes}")
    private long codeTtlMinutes;

    @Value("${application.sms-verification.code-length}")
    private int codeLength;

    @Override
    public String saveCode(String phoneNumber) {
        String key = prefix + phoneNumber;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }

        String code = CodeGeneratorUtil.generateNumericCode(codeLength);
        redisTemplate.opsForValue().set(
                key,
                code,
                codeTtlMinutes,
                TimeUnit.MINUTES
        );

        return code;
    }

    @Override
    @Transactional
    public TokenResDto smsVerification(SmsVerificationReqDto dto, HttpServletResponse response) {
        String key = prefix + dto.getPhoneNumber();
        String storedCode = redisTemplate.opsForValue().get(key);

        if (storedCode == null) {
            throw new AppException(
                    ErrorMessage.CODE_EXPIRED_OR_NOT_FOUND,
                    HttpStatus.BAD_REQUEST
            );
        }

        if (!storedCode.equals(dto.getCode())) {
            throw new AppException(
                    ErrorMessage.INVALID_VERIFICATION_CODE,
                    HttpStatus.BAD_REQUEST
            );
        }

        var user = getByPhoneNumber(dto.getPhoneNumber());

        if (user.getVerificationStatus().equals(VerificationStatus.PHONE_NOT_VERIFIED)) {
            user.setVerificationStatus(VerificationStatus.INFO_NOT_ENTERED);
        }

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        redisTemplate.delete(key);

        return tokenMapper.toDto(accessToken, refreshToken);
    }

    @Override
    public void resendCode(PhoneNumberReqDto dto) {
        String code = this.saveCode(dto.getPhoneNumber());
        smsService.sendVerificationCode(dto.getPhoneNumber(), code);
    }

    @Override
    public Map<String, String> getAllCodes() {
        Set<String> keys = redisTemplate.keys(prefix + "*");
        Map<String, String> result = new HashMap<>();
        if (keys != null) {
            for (String key : keys) {
                String code = redisTemplate.opsForValue().get(key);
                result.put(key, code);
            }
        }
        return result;
    }

    private User getByPhoneNumber(String phoneNumber) {

        return userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
    }
}
