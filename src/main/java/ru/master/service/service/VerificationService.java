package ru.master.service.service;

import ru.master.service.model.dto.request.ResendCodeDto;
import ru.master.service.model.dto.request.VerificationDto;

import java.util.Map;

public interface VerificationService {

    String saveCode(String phoneNumber);

    void verifyCode(VerificationDto dto);

    void resendCode(ResendCodeDto dto);

    Map<String, String> getAllCodes(); //TODO для тестирование, нужно потом удалить
}
