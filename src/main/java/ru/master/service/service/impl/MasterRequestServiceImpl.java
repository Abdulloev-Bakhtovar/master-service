package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.model.User;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterRequestMapper;
import ru.master.service.repository.MasterRequestRepo;
import ru.master.service.service.MasterRequestService;
@Service
@Transactional
@RequiredArgsConstructor
public class MasterRequestServiceImpl implements MasterRequestService {

    private final MasterRequestRepo masterRequestRepo;
    private final MasterRequestMapper masterRequestMapper;

    @Override
    public void create(User user) {

        if (masterRequestRepo.existsByUserId(user.getId())) {
            throw new AppException(
                    ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                    HttpStatus.CONFLICT);
        }

        var request = masterRequestMapper.toMasterRequestEntity(user, VerificationStatus.UNDER_REVIEW);
        masterRequestRepo.save(request);
    }
}
