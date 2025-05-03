package ru.master.service.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import ru.master.service.auth.model.dto.request.AccountVerifyDto;
import ru.master.service.auth.model.dto.request.PhoneNumberDto;
import ru.master.service.auth.model.dto.response.TokenDto;

import java.util.Map;

public interface VerificationService {

    String saveCode(String phoneNumber);

    TokenDto verifyCode(AccountVerifyDto dto, HttpServletResponse response);

    void resendCode(PhoneNumberDto dto);

    Map<String, String> getAllCodes(); //TODO для тестирование, нужно потом удалить
}
