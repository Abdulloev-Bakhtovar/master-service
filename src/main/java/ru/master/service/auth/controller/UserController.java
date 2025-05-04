package ru.master.service.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.auth.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{phoneNumber}/verification-status")
    public EnumResDto getVerificationStatusByPhoneNumber(@PathVariable String phoneNumber) {
        return userService.getVerificationStatusByPhoneNumber(phoneNumber);
    }

}
