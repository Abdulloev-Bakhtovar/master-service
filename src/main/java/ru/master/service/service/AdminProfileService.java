package ru.master.service.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.model.dto.request.CreateAdminProfileReqDto;
import ru.master.service.model.dto.request.LoginAdminProfileReqDto;

public interface AdminProfileService extends UserDetailsService {

    void create(CreateAdminProfileReqDto reqDto);

    TokenDto login(LoginAdminProfileReqDto reqDto);

    UserDetails loadUserByUsername(String email);
}
