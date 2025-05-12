package ru.master.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.model.AdminProfile;

@Component
public class AuthUtil {

    private static AuthenticationManager authenticationManager;

    public AuthUtil(AuthenticationManager authenticationManager) {
        AuthUtil.authenticationManager = authenticationManager;
    }

    public User getAuthenticatedUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }

        throw new AppException(
                ErrorMessage.USER_NOT_AUTHENTICATED,
                HttpStatus.UNAUTHORIZED
        );
    }

    public AdminProfile getAuthenticatedAdmin() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof AdminProfile admin) {
            return admin;
        }

        throw new AppException(
                ErrorMessage.ADMIN_NOT_AUTHENTICATED,
                HttpStatus.UNAUTHORIZED
        );
    }

    public static void validateCredentials(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new AppException("Invalid credentials for email: " + email, HttpStatus.UNAUTHORIZED);
        }
    }

}

