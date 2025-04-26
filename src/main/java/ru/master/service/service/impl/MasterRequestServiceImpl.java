package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.mapper.UserMapper;
import ru.master.service.auth.model.User;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.mapper.MasterRequestMapper;
import ru.master.service.model.dto.MasterRequestDto;
import ru.master.service.model.dto.NewMasterRequestDto;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.repository.MasterRequestRepo;
import ru.master.service.repository.MasterSubServiceRepo;
import ru.master.service.repository.UserAgreementRepo;
import ru.master.service.service.MasterRequestService;
import ru.master.service.util.AuthUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterRequestServiceImpl implements MasterRequestService {

    private final MasterRequestRepo masterRequestRepo;
    private final MasterRequestMapper masterRequestMapper;
    private final AuthUtils authUtils;
    private final UserRepo userRepo;
    private final MasterProfileRepo masterProfileRepo;
    private final MasterSubServiceRepo masterSubServiceRepo;
    private final MasterProfileMapper masterProfileMapper;
    private final UserMapper userMapper;
    private final UserAgreementRepo userAgreementRepo;

    @Override
    public List<NewMasterRequestDto> getAll() {

        List<NewMasterRequestDto> dtos = new ArrayList<>();
        var users = userRepo.findByVerificationStatus(VerificationStatus.UNDER_REVIEW);

        for (var user : users) {
            var exsistUser = userRepo.findById(user.getId())
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            var masterProfile = masterProfileRepo.findByUserId(exsistUser.getId())
                    .orElseThrow(() -> new AppException(
                            "Master profile " + ErrorMessage.ENTITY_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            var masterRequest = masterRequestRepo.findByUserId(user.getId())
                    .orElseThrow(() -> new AppException(
                            "Master request " + ErrorMessage.ENTITY_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            NewMasterRequestDto masterProfileDto = masterProfileMapper.toDto(masterProfile, masterRequest);
            dtos.add(masterProfileDto);
        }

        return dtos;
    }

    @Override
    public MasterRequestDto getById(UUID id) {

        var masterRequest = masterRequestRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        "Master request " + ErrorMessage.ENTITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var user = masterRequest.getUser();
        var masterProfile = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        "Master profile " + ErrorMessage.ENTITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var userAgreement = userAgreementRepo.findByUserId(user.getId());
        var userDto = userMapper.toDto(user);
        var masterSubServices = masterSubServiceRepo.findAllByMasterProfileId(masterProfile.getId());

        var masterProfileDto = masterProfileMapper.toDto(masterProfile, masterSubServices, userAgreement);

        return masterRequestMapper.toDto(masterRequest, masterProfileDto, userDto);
    }

    @Override
    public void create(User user) {

        if (masterRequestRepo.existsByUserId(user.getId())) {
            throw new AppException(
                    "Application " + ErrorMessage.ENTITY_ALREADY_EXISTS,
                    HttpStatus.CONFLICT);
        }

        var application = masterRequestMapper.toEntity(user, VerificationStatus.UNDER_REVIEW);
        userRepo.save(user);
        masterRequestRepo.save(application);
    }

    @Override
    public void approve(MasterRequestDto dto) {

        processMasterRequest(dto, VerificationStatus.APPROVED);
    }

    @Override
    public void reject(MasterRequestDto dto) {

        processMasterRequest(dto, VerificationStatus.REJECTED);
    }

    private void processMasterRequest(MasterRequestDto dto, VerificationStatus rejected) {
        var admin = authUtils.getAuthenticatedUser();

        admin = userRepo.findById(admin.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var masterRequest = masterRequestRepo.findById(dto.getId())
                .orElseThrow(() -> new AppException(
                        "Master request " + ErrorMessage.ENTITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        masterRequestMapper.toEntity(admin, masterRequest, rejected, dto.getRejectionReason());
        masterRequestRepo.save(masterRequest);
    }
}
