package ru.master.service.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.auth.repository.RoleRepo;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.SmsService;
import ru.master.service.auth.service.UserService;
import ru.master.service.auth.service.VerificationService;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.constants.RoleName;
import ru.master.service.exception.AppException;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.dto.PhoneNumberDto;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final VerificationService verificationService;
    private final SmsService smsService;
    private final RoleRepo roleRepo;

    @Override
    public void register(UserDto dto) {

        if (userRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new AppException(
                    ErrorMessage.USER_ALREADY_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        var role = roleRepo.findByName(dto.getRole().getName())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.INVALID_ROLE,
                        HttpStatus.BAD_REQUEST
                ));

        if (role.getName().equals(RoleName.ADMIN)) {
            throw new AppException(
                    ErrorMessage.ASSIGN_ADMIN_ROLE_FORBIDDEN,
                    HttpStatus.FORBIDDEN
            );
        }

        var user = userMapper.toEntity(dto, role);
        userRepo.save(user);

        String code = verificationService.saveCode(dto.getPhoneNumber());
        smsService.sendVerificationCode(dto.getPhoneNumber(), code);
    }

    @Override
    public void login(PhoneNumberDto dto) {

        var user = userRepo.findByPhoneNumber(dto.getPhoneNumber())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        // TODO
        if (!user.isVerified()) {
            throw new AppException(
                    ErrorMessage.USER_NOT_VERIFIED,
                    HttpStatus.FORBIDDEN
            );
        }

        String code = verificationService.saveCode(dto.getPhoneNumber());
        smsService.sendVerificationCode(dto.getPhoneNumber(), code);
    }
}
