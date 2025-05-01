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
import ru.master.service.model.MasterRequest;
import ru.master.service.model.dto.request.MasterRequestRejectDto;
import ru.master.service.model.dto.response.MasterRequestDto;
import ru.master.service.model.dto.response.NewMasterRequestDto;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.repository.MasterRequestRepo;
import ru.master.service.repository.MasterSubServiceRepo;
import ru.master.service.repository.UserAgreementRepo;
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
    private final AuthUtil authUtil;
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
                            ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            var masterRequest = masterRequestRepo.findByUserId(user.getId())
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            var masterProfileDto = masterProfileMapper.toNewMasterRequestDto(masterProfile, masterRequest);
            dtos.add(masterProfileDto);
        }

        return dtos;
    }

    @Override
    public MasterRequestDto getById(UUID id) {

        var masterRequest = masterRequestRepo.findById(id)
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

        var userAgreement = userAgreementRepo.findByUserId(user.getId());
        var userDto = userMapper.toDto(user);
        var masterSubServices = masterSubServiceRepo.findAllByMasterProfileId(masterProfile.getId());

        var masterProfileDto = masterProfileMapper.toMasterRequestDto(masterProfile, masterSubServices, userAgreement);

        return masterRequestMapper.toMasterRequestDto(masterRequest, masterProfileDto, userDto);
    }

    @Override
    public void create(User user) {

        if (masterRequestRepo.existsByUserId(user.getId())) {
            throw new AppException(
                    ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                    HttpStatus.CONFLICT);
        }

        var request = masterRequestMapper.toEntity(user, VerificationStatus.UNDER_REVIEW);
        //userRepo.save(user);
        masterRequestRepo.save(request);
    }

    @Override
    public void reject(MasterRequestRejectDto rejectDto) {
        var masterRequest = getMasterRequest(rejectDto.getId(),
                VerificationStatus.REJECTED,
                rejectDto.getRejectionReason()
        );
        masterRequestRepo.save(masterRequest);
    }

    @Override
    public void approve(UUID id) {
        var masterRequest = getMasterRequest(id, VerificationStatus.APPROVED, null);
        masterRequestRepo.save(masterRequest);
    }

    private MasterRequest getMasterRequest(UUID rejectDto, VerificationStatus rejected, String rejectionReason) {
        var admin = authUtil.getAuthenticatedUser();

        admin = userRepo.findById(admin.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var masterRequest = masterRequestRepo.findById(rejectDto)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_REQUEST_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        masterRequestMapper.toEntityWithAdmin(
                admin,
                masterRequest,
                rejected,
                rejectionReason
        );
        return masterRequest;
    }
}
