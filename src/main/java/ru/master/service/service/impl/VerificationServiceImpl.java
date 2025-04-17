package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.model.User;
import ru.master.service.model.dto.request.ResendCodeDto;
import ru.master.service.model.dto.request.VerificationDto;
import ru.master.service.repository.UserRepo;
import ru.master.service.service.SmsService;
import ru.master.service.service.VerificationService;
import ru.master.service.util.CodeGeneratorUtil;

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
    public void verifyCode(VerificationDto dto) {
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
        updateVerified(user);
        redisTemplate.delete(key);
    }

    @Override
    public void resendCode(ResendCodeDto dto) {

        var user = getByPhoneNumber(dto.getPhoneNumber());

        if (user.isVerified()) {
            throw new AppException(
                    ErrorMessage.USER_ALREADY_VERIFIED,
                    HttpStatus.BAD_REQUEST
            );
        }

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

    public void updateVerified(User user) {
        user.setVerified(true);
        userRepo.save(user);
    }
}
