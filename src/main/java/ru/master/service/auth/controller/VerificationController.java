package ru.master.service.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.request.AccountVerifyDto;
import ru.master.service.auth.model.dto.request.PhoneNumberDto;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.auth.service.VerificationService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto verifyCode(@RequestBody AccountVerifyDto accountVerifyDto) {
        return verificationService.verifyCode(accountVerifyDto);
    }

    @PostMapping("/resend-code")
    @ResponseStatus(HttpStatus.OK)
    public void resendVerificationCode(@RequestBody PhoneNumberDto dto) {
        verificationService.resendCode(dto);
    }


    @GetMapping("/get-all-code")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getAllVerificationCode() {
        return verificationService.getAllCodes();
    }
}
