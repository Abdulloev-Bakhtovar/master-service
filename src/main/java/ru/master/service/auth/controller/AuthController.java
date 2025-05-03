package ru.master.service.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.request.RefreshTokenDto;
import ru.master.service.auth.model.dto.request.RegisterAndLoginDto;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.auth.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register-or-login")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerOrLogin(@RequestBody RegisterAndLoginDto registerAndLoginDto) {
        userService.registerOrLogin(registerAndLoginDto);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) {
        return userService.refreshToken(refreshTokenDto);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestBody TokenDto tokenDto) {
        userService.logout(tokenDto);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@RequestBody TokenDto tokenDto) {
        userService.delete(tokenDto);
    }
}
