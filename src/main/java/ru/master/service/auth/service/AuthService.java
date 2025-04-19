package ru.master.service.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.master.service.auth.model.User;
import ru.master.service.auth.model.dto.PhoneNumberDto;
import ru.master.service.auth.model.dto.TokenDto;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.constants.VerificationStatus;

public interface AuthService {

    void register(UserDto userDto);

    void login(PhoneNumberDto loginDto);

    TokenDto refreshToken(HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);

    void delete(HttpServletRequest request, HttpServletResponse response);

   void updateVerificationStatus(User user, VerificationStatus verificationStatus);
}
