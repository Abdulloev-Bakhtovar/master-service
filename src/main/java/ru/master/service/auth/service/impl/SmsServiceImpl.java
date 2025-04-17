package ru.master.service.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.master.service.auth.service.SmsService;

@Service
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Override
    public void sendVerificationCode(String phoneNumber, String verificationCode) {
        // TODO написать реализацию
    }
}
