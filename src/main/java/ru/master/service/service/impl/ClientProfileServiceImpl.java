package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.auth.service.UserService;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.VerificationStatus;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.ClientProfileMapper;
import ru.master.service.model.ClientProfile;
import ru.master.service.model.dto.request.CreateClientProfileReqDto;
import ru.master.service.model.dto.response.ClientInfoForCreateOrderResDto;
import ru.master.service.repository.ClientProfileRepo;
import ru.master.service.service.CityService;
import ru.master.service.service.ClientProfileService;
import ru.master.service.service.UserAgreementService;
import ru.master.service.util.AuthUtil;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final ClientProfileRepo clientProfileRepo;
    private final ClientProfileMapper clientProfileMapper;
    private final AuthUtil authUtil;
    private final UserRepo userRepo;
    private final UserAgreementService userAgreementService;
    private final CityService cityService;
    private final UserService userService;

    @Override
    public void create(CreateClientProfileReqDto reqDto) {

        var user = authUtil.getAuthenticatedUser();

        user = userRepo.findById(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.USER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        if (clientProfileRepo.existsByUserId(user.getId())) {
            throw new AppException(
                    ErrorMessage.CLIENT_PROFILE_EXISTS,
                    HttpStatus.CONFLICT
            );
        }

        var city = cityService.getById(reqDto.getCityId());

        userAgreementService.create(reqDto.getUserAgreementDto(), user);

        var clientProfileEntity = clientProfileMapper.toEntity(reqDto, user, city);

        userService.updateVerificationStatus(user, VerificationStatus.APPROVED);

        clientProfileRepo.save(clientProfileEntity);
    }

    @Override
    public ClientInfoForCreateOrderResDto getClientInfo() {
        var user = authUtil.getAuthenticatedUser();

        ClientProfile profile = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return clientProfileMapper.toClientInfoForCreateOrderResDto(profile);
    }
}
