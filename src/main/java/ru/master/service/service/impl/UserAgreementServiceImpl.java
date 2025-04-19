package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.model.User;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.UserAgreementMapper;
import ru.master.service.model.dto.UserAgreementDto;
import ru.master.service.repository.UserAgreementRepo;
import ru.master.service.service.UserAgreementService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAgreementServiceImpl implements UserAgreementService {

    private final UserAgreementRepo userAgrRepo;
    private final UserAgreementMapper userAgrMapper;

    @Override
    public void create(UserAgreementDto dto, User user) {

        if (userAgrRepo.existsByUserId(user.getId())) {
            throw new AppException(
                    ErrorMessage.USER_AGREEMENT_ALREADY_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        var userAgr = userAgrMapper.toDto(dto, user);
        userAgrRepo.save(userAgr);
    }
}
