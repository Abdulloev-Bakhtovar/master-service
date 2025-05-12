package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.model.dto.request.CreateAdminProfileReqDto;
import ru.master.service.model.dto.request.LoginAdminProfileReqDto;
import ru.master.service.service.AdminProfileService;

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
}
