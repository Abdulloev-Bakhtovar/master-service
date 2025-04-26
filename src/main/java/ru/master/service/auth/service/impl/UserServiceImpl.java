package ru.master.service.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.UserService;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    @Transactional(readOnly = true)
    public String getVerificationStatusByPhoneNumber(String phoneNumber) {
        var user = userRepo.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return user.getVerificationStatus().toString();
    }
}
