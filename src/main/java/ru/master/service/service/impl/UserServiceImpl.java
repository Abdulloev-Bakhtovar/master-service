package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.UserMapper;
import ru.master.service.model.dto.request.UserDto;
import ru.master.service.repository.UserRepo;
import ru.master.service.service.SmsService;
import ru.master.service.service.UserService;
import ru.master.service.service.VerificationCodeService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final VerificationCodeService verificationCodeService;
    private final SmsService smsService;

    @Override
    public void register(UserDto dto) {

        if (userRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new AppException(
                    ErrorMessage.USER_ALREADY_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        var user = userMapper.toEntity(dto);
        userRepo.save(user);

        String code = verificationCodeService.saveCode(dto.getPhoneNumber());
        smsService.sendVerificationCode(dto.getPhoneNumber(), code);
    }
}
