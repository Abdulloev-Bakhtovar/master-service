package ru.master.service.admin.service;

import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.master.service.admin.model.dto.EmailDto;
import ru.master.service.admin.model.dto.request.ResetPasswordReqDto;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.admin.model.dto.request.CreateAdminProfileReqDto;
import ru.master.service.admin.model.dto.request.LoginAdminProfileReqDto;

public interface AdminProfileService extends UserDetailsService {

    void create(CreateAdminProfileReqDto reqDto);

    TokenDto login(LoginAdminProfileReqDto reqDto);

    void logout(TokenDto tokenDto);

    UserDetails loadUserByUsername(String email);

    void resetPasswordRequest(EmailDto reqDto) throws MessagingException;

    void confirmTokenFromEmailAndResetPass(ResetPasswordReqDto resetPasswordReqDto);
}
