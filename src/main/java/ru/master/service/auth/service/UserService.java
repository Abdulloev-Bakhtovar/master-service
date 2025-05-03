package ru.master.service.auth.service;

import ru.master.service.auth.model.dto.response.EnumDto;
import ru.master.service.auth.model.dto.request.RefreshTokenDto;
import ru.master.service.auth.model.dto.request.RegisterAndLoginDto;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;

public interface UserService {

    void registerOrLogin(RegisterAndLoginDto registerAndLoginDto);

    TokenDto refreshToken(RefreshTokenDto refreshTokenDto);

    void logout(TokenDto tokenDto);

    void delete(TokenDto tokenDto);

    void updateVerificationStatus(User user, VerificationStatus verificationStatus);

    EnumDto getVerificationStatusByPhoneNumber(String phoneNumber);
}
