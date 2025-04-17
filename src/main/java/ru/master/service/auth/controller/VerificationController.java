package ru.master.service.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.TokenDto;
import ru.master.service.auth.service.VerificationService;
import ru.master.service.auth.model.dto.PhoneNumberDto;
import ru.master.service.auth.model.dto.VerificationCodeDto;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto verifyCode(@RequestBody VerificationCodeDto dto, HttpServletResponse response) {
        return verificationService.verifyCode(dto, response);
    }

    @PostMapping("/resend-code")
    public void resendVerificationCode(@RequestBody PhoneNumberDto dto) {
        verificationService.resendCode(dto);
    }


    @GetMapping("/get-all-code")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getAllVerificationCode() {
        return verificationService.getAllCodes();
    }
}
