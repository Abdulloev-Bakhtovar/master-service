package ru.master.service.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.master.service.auth.service.UserService;
import ru.master.service.model.dto.EnumDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{phoneNumber}/verification-status")
    public EnumDto getVerificationStatusByPhoneNumber(@PathVariable String phoneNumber) {
        return userService.getVerificationStatusByPhoneNumber(phoneNumber);
    }

}
