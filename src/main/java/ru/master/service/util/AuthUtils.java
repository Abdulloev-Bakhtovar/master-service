package ru.master.service.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;

@Component
public class AuthUtils {

    public User getAuthenticatedUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }

        throw new AppException(ErrorMessage.USER_NOT_AUTHENTICATED, HttpStatus.UNAUTHORIZED);
    }
}

