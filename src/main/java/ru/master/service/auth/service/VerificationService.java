package ru.master.service.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import ru.master.service.auth.model.dto.TokenDto;
import ru.master.service.auth.model.dto.PhoneNumberDto;
import ru.master.service.auth.model.dto.VerificationCodeDto;

import java.util.Map;

public interface VerificationService {

    String saveCode(String phoneNumber);

    TokenDto verifyCode(VerificationCodeDto dto, HttpServletResponse response);

    void resendCode(PhoneNumberDto dto);

    Map<String, String> getAllCodes(); //TODO для тестирование, нужно потом удалить
}
