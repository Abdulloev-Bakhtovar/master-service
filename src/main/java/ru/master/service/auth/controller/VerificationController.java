package ru.master.service.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.request.SmsVerificationReqDto;
import ru.master.service.auth.model.dto.request.PhoneNumberReqDto;
import ru.master.service.auth.model.dto.response.TokenResDto;
import ru.master.service.auth.service.VerificationService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/verify-sms")
    @ResponseStatus(HttpStatus.OK)
    public TokenResDto SmsVerification(@RequestBody SmsVerificationReqDto smsVerificationReqDto, HttpServletResponse response) {
        return verificationService.smsVerification(smsVerificationReqDto, response);
    }

    @PostMapping("/resend-code")
    @ResponseStatus(HttpStatus.OK)
    public void resendVerificationCode(@RequestBody PhoneNumberReqDto dto) {
        verificationService.resendCode(dto);
    }


    @GetMapping("/get-all-code")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getAllVerificationCode() {
        return verificationService.getAllCodes();
    }
}
