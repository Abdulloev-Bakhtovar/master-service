package ru.master.service.admin.service;

import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.master.service.admin.model.dto.EmailDto;
import ru.master.service.admin.model.dto.ResetPasswordDto;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.admin.model.dto.CreateAdminProfileReqDto;
import ru.master.service.admin.model.dto.LoginAdminProfileReqDto;

public interface AdminProfileService extends UserDetailsService {

    void create(CreateAdminProfileReqDto reqDto);

    TokenDto login(LoginAdminProfileReqDto reqDto);

    UserDetails loadUserByUsername(String email);

    void resetPasswordRequest(EmailDto reqDto) throws MessagingException;

    void confirmTokenFromEmailAndResetPass(ResetPasswordDto resetPasswordDto);
}
