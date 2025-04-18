package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.auth.repository.UserRepo;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.ClientProfileMapper;
import ru.master.service.model.dto.ClientProfileDto;
import ru.master.service.repository.ClientProfileRepo;
import ru.master.service.service.CityService;
import ru.master.service.service.ClientProfileService;
import ru.master.service.service.UserAgreementService;
import ru.master.service.util.AuthUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final ClientProfileRepo clientProfileRepo;
    private final ClientProfileMapper clientProfileMapper;
    private final AuthUtils authUtils;
    private final UserRepo userRepo;
    private final UserAgreementService userAgreementService;
    private final CityService cityService;

    @Override
    public void create(ClientProfileDto dto) {

        var user = authUtils.getAuthenticatedUser();

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

        var city = cityService.getById(dto.getCityDto().getId());

        userAgreementService.create(dto.getUserAgreementDto(), user);

        var clientProfile = clientProfileMapper.toEntity(dto, user, city);
        clientProfileRepo.save(clientProfile);
    }
}
