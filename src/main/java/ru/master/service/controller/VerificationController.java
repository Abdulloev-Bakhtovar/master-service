package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.ResendCodeDto;
import ru.master.service.model.dto.request.VerificationDto;
import ru.master.service.service.VerificationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public void verifyCode(@RequestBody VerificationDto dto) {
        verificationService.verifyCode(dto);
    }

    @PostMapping("/resend-code")
    public void resendVerificationCode(@RequestBody ResendCodeDto dto) {
        verificationService.resendCode(dto);
    }
}
