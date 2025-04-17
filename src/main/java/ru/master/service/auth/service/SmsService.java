package ru.master.service.auth.service;

public interface SmsService {

    void sendVerificationCode(String phoneNumber, String verificationCode);
}
