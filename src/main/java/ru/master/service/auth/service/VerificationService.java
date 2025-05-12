package ru.master.service.auth.service;

import ru.master.service.admin.model.dto.request.ResetPasswordReqDto;
import ru.master.service.auth.model.dto.request.AccountVerifyDto;
import ru.master.service.auth.model.dto.request.PhoneNumberDto;
import ru.master.service.auth.model.dto.response.TokenDto;

import java.util.Map;

public interface VerificationService {

    String saveCode(String phoneNumber);

    TokenDto verifyCode(AccountVerifyDto dto);

    void resendCode(PhoneNumberDto dto);

    boolean isValidCodeForResetPass(ResetPasswordReqDto dto);

    Map<String, String> getAllCodes(); //TODO для тестирование, нужно потом удалить
}
