package ru.master.service.admin.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.admin.model.dto.CreateAdminProfileReqDto;
import ru.master.service.admin.model.dto.EmailDto;
import ru.master.service.admin.model.dto.LoginAdminProfileReqDto;
import ru.master.service.admin.model.dto.ResetPasswordDto;
import ru.master.service.admin.service.AdminProfileService;
import ru.master.service.auth.model.dto.response.TokenDto;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminProfileService adminProfileService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateAdminProfileReqDto reqDto) {
        adminProfileService.create(reqDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@RequestBody LoginAdminProfileReqDto reqDto) {
        return adminProfileService.login(reqDto);
    }

    @PatchMapping("/password/reset-request")
    @ResponseStatus(HttpStatus.OK)
    public void resetPasswordRequest(@RequestBody EmailDto reqDto) throws MessagingException {
        adminProfileService.resetPasswordRequest(reqDto);
    }

    @PatchMapping("/password/reset-confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmResetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        adminProfileService.confirmTokenFromEmailAndResetPass(resetPasswordDto);
    }
}
