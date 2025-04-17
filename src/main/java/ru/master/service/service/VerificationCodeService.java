package ru.master.service.service;

import java.util.Map;

public interface VerificationCodeService {

    String saveCode(String phoneNumber);

    boolean verifyCode(String phoneNumber, String code);

    Map<String, String> getAllCodes(); //TODO для тестирование, нужно потом удалить
}
