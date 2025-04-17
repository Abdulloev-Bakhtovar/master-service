package ru.master.service.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.auth.service.UserService;
import ru.master.service.auth.model.dto.PhoneNumberDto;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserDto userDto) {
        userService.register(userDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody PhoneNumberDto loginDto) {
        userService.login(loginDto);
    }
}
