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
import ru.master.service.mapper.MasterApplicationMapper;
import ru.master.service.mapper.MasterProfileMapper;
import ru.master.service.model.MasterApplication;
import ru.master.service.model.dto.MasterApplicationDto;
import ru.master.service.repository.MasterApplicationRepo;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.repository.MasterSubServiceRepo;
import ru.master.service.repository.UserAgreementRepo;
import ru.master.service.service.MasterApplicationService;
import ru.master.service.util.AuthUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterApplicationServiceImpl implements MasterApplicationService {

    private final MasterApplicationRepo masterApplicationRepo;
    private final MasterApplicationMapper masterApplicationMapper;
    private final AuthUtils authUtils;
    private final UserRepo userRepo;
    private final MasterProfileRepo masterProfileRepo;
    private final MasterSubServiceRepo masterSubServiceRepo;
    private final MasterProfileMapper masterProfileMapper;
    private final UserMapper userMapper;
    private final UserAgreementRepo userAgreementRepo;

    @Override
    public List<MasterApplicationDto> getAll() {
        List<MasterApplicationDto> dtos = new ArrayList<>();
        var masterApplications = masterApplicationRepo.findAll();

        for (var masterApplication : masterApplications) {
            var user = userRepo.findById(masterApplication.getUser().getId())
                    .orElseThrow(() -> new AppException(
                            ErrorMessage.USER_NOT_FOUND,
                            HttpStatus.NOT_FOUND
                    ));
            var masterProfile = masterProfileRepo.findByUserId(user.getId())
                    .orElseThrow(() -> new AppException(
                    "Master profile " + ErrorMessage.ENTITY_NOT_FOUND,
                    HttpStatus.NOT_FOUND
            ));

            var userAgreement = userAgreementRepo.findByUserId(user.getId());

            var userDto = userMapper.toDto(user);

            var masterSubServices = masterSubServiceRepo.findAllByMasterProfileId(masterProfile.getId());

            var masterProfileDto = masterProfileMapper.toDto(masterProfile, masterSubServices, userAgreement);

            var masterApplicationDto = MasterApplicationDto.builder()
                    .id(masterApplication.getId())
                    .masterProfileDto(masterProfileDto)
                    .userDto(userDto)
                    .build();

            dtos.add(masterApplicationDto);
        }

        return dtos;
    }

    @Override
    public void create(User user) {

        if (masterApplicationRepo.existsByUserId(user.getId())) {
            throw new AppException(
                    "Application " + ErrorMessage.ENTITY_ALREADY_EXISTS,
                    HttpStatus.CONFLICT);
        }

        var application = masterApplicationMapper.toEntity(user, VerificationStatus.UNDER_REVIEW);
        userRepo.save(user);
        masterApplicationRepo.save(application);
    }

    @Override
    public void approve(MasterApplicationDto dto) {

        var existsApplication = getExistsApplication(dto, VerificationStatus.APPROVED);
        masterApplicationRepo.save(existsApplication);
    }

    @Override
    public void reject(MasterApplicationDto dto) {

        var existsApplication = getExistsApplication(dto, VerificationStatus.REJECTED);
        masterApplicationRepo.save(existsApplication);

    }

    private MasterApplication getExistsApplication(MasterApplicationDto dto, VerificationStatus rejected) {
        var admin = authUtils.getAuthenticatedUser();

        admin = userRepo.findById(admin.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var user = userRepo.findById(dto.getUserDto().getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var existsApplication = getByUserId(user);

        masterApplicationMapper.toEntity(admin, user, rejected, existsApplication);
        userRepo.save(user);

        return existsApplication;
    }

    public MasterApplication getByUserId(User user) {

        return masterApplicationRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        "Application " + ErrorMessage.ENTITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
    }
}
