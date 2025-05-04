package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.mapper.MasterRequestMapper;
import ru.master.service.model.dto.request.RejectMasterRequestReqDto;
import ru.master.service.model.dto.response.MasterRequestResDto;
import ru.master.service.model.dto.response.NewMasterRequestResDto;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.repository.MasterRequestRepo;
import ru.master.service.service.MasterRequestService;
import ru.master.service.util.AuthUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterRequestServiceImpl implements MasterRequestService {

    private final MasterRequestRepo masterRequestRepo;
    private final MasterRequestMapper masterRequestMapper;
    private final UserRepo userRepo;
    private final MasterProfileRepo masterProfileRepo;
    private final AuthUtil authUtil;
    private final MasterProfileMapper masterProfileMapper;
    private final UserMapper userMapper;

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

    @Override
    public List<NewMasterRequestResDto> getAll() {
        List<NewMasterRequestResDto> dtos = new ArrayList<>();
        var users = userRepo.findByVerificationStatus(VerificationStatus.UNDER_REVIEW);

        for (var user : users) {
            var exsistUser = userRepo.findById(user.getId())
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            var masterProfile = masterProfileRepo.findByUserId(exsistUser.getId())
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            var masterRequest = masterRequestRepo.findByUserId(user.getId())
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            NewMasterRequestResDto masterProfileDto = masterRequestMapper.toNewMasterRequestResDto(masterProfile, masterRequest);
            dtos.add(masterProfileDto);
        }

        return dtos;
    }

    @Override
    public void approve(UUID requestId) {
        var admin = authUtil.getAuthenticatedUser();

        admin = userRepo.findById(admin.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var masterRequest = masterRequestRepo.findById(requestId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        masterRequestMapper.toEntityForApprove(admin, masterRequest);
        masterRequestRepo.save(masterRequest);
    }

    @Override
    public void reject(UUID requestId, RejectMasterRequestReqDto reqDto) {
        var admin = authUtil.getAuthenticatedUser();

        admin = userRepo.findById(admin.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var masterRequest = masterRequestRepo.findById(requestId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        masterRequestMapper.toEntityForReject(admin, masterRequest, reqDto);
        masterRequestRepo.save(masterRequest);
    }

    @Override
    public MasterRequestResDto getById(UUID requestId) {
        var masterRequest = masterRequestRepo.findById(requestId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var user = masterRequest.getUser();
        var masterProfile = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return masterRequestMapper.toMasterRequestResDto(masterRequest, user, masterProfile);
    }
}
