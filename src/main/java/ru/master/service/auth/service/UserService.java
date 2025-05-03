package ru.master.service.auth.service;

import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.auth.model.dto.request.RefreshTokenReqDto;
import ru.master.service.auth.model.dto.request.RegisterOrLoginReqDto;
import ru.master.service.auth.model.dto.response.TokenResDto;
import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;

public interface UserService {

    void registerOrLogin(RegisterOrLoginReqDto registerOrLoginReqDto);

    TokenResDto refreshToken(RefreshTokenReqDto refreshTokenReqDto);

    void logout(TokenResDto tokenResDto);

    void delete(TokenResDto tokenResDto);

    void updateVerificationStatus(User user, VerificationStatus verificationStatus);

    EnumResDto getVerificationStatusByPhoneNumber(String phoneNumber);
}
