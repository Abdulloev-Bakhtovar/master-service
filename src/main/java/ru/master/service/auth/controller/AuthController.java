package ru.master.service.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.request.RefreshTokenReqDto;
import ru.master.service.auth.model.dto.request.RegisterOrLoginReqDto;
import ru.master.service.auth.model.dto.response.TokenResDto;
import ru.master.service.auth.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register-or-login")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerOrLogin(@RequestBody RegisterOrLoginReqDto registerOrLoginReqDto) {
        userService.registerOrLogin(registerOrLoginReqDto);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenResDto refreshToken(@RequestBody RefreshTokenReqDto refreshTokenReqDto) {
        return userService.refreshToken(refreshTokenReqDto);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestBody TokenResDto tokenResDto) {
        userService.logout(tokenResDto);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@RequestBody TokenResDto tokenResDto) {
        userService.delete(tokenResDto);
    }
}
