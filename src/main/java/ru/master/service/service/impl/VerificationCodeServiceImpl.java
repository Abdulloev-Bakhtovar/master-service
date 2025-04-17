package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.master.service.service.SmsService;
import ru.master.service.service.VerificationCodeService;
import ru.master.service.util.CodeGeneratorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final SmsService smsService;
    private final RedisTemplate<String, String> redisTemplate;

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
    public boolean verifyCode(String phoneNumber, String code) {
        String key = prefix + phoneNumber;
        String stored = redisTemplate.opsForValue().get(key);
        if (stored != null && stored.equals(code)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
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
}
