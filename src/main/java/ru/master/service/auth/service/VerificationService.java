package ru.master.service.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import ru.master.service.auth.model.dto.request.SmsVerificationReqDto;
import ru.master.service.auth.model.dto.request.PhoneNumberReqDto;
import ru.master.service.auth.model.dto.response.TokenResDto;

import java.util.Map;

public interface VerificationService {

    String saveCode(String phoneNumber);

    TokenResDto smsVerification(SmsVerificationReqDto dto, HttpServletResponse response);

    void resendCode(PhoneNumberReqDto dto);

    Map<String, String> getAllCodes(); //TODO для тестирование, нужно потом удалить
}
