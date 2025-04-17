package ru.master.service.service;

public interface SmsService {

    void sendVerificationCode(String phoneNumber, String verificationCode);
}
