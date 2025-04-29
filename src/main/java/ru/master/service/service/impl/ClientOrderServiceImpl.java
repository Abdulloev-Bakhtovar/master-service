package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constants.EntityName;
import ru.master.service.constants.ErrorMessage;
import ru.master.service.constants.Role;
import ru.master.service.constants.ClientOrderStatus;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.ClientOrderMapper;
import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ClientOrderDto;
import ru.master.service.model.dto.request.ClientOrderInfoDto;
import ru.master.service.repository.*;
import ru.master.service.service.ClientOrderService;
import ru.master.service.util.AuthUtils;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientOrderServiceImpl implements ClientOrderService {

    private final ClientOrderRepo clientOrderRepo;
    private final CityRepo cityRepo;
    private final ClientProfileRepo clientProfileRepo;
    private final ServiceCategoryRepo serviceCategoryRepo;
    private final SubServiceCategoryRepo subServiceCategoryRepo;
    private final ClientOrderMapper clientOrderMapper;
    private final AuthUtils authUtils;
    private final MasterProfileRepo masterProfileRepo;

    @Override
    @Transactional(readOnly = true)
    public ClientOrderInfoDto getById(UUID id) {
        var entity = clientOrderRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        String.format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.CLIENT_ORDER.get()),
                        HttpStatus.NOT_FOUND
                ));

        return clientOrderMapper.orderInfoDto(entity);
    }

    @Override
    public IdDto create(ClientOrderDto dto) {

        var user = authUtils.getAuthenticatedUser();

        var city = cityRepo.findById(dto.getCityDto().getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var clientProfile = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        "Client profile " + ErrorMessage.ENTITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var serviceCategory = serviceCategoryRepo.findById(dto.getServiceCategoryDto().getId())
                .orElseThrow(() -> new AppException(
                        "Service category " + ErrorMessage.ENTITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var subServiceCategory = subServiceCategoryRepo.findById(dto.getServiceCategoryDto()
                        .getSubServiceCategoryDtos()
                        .getFirst().getId()
                )
                .orElseThrow(() -> new AppException(
                        "Subservice category " + ErrorMessage.ENTITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        if (!serviceCategory.getSubServices().contains(subServiceCategory)) {
            throw new AppException(
                    "Subservice does not belong to selected service",
                    HttpStatus.BAD_REQUEST
            );
        }

        var serviceRequest = clientOrderMapper.toEntity(
                dto,
                city,
                clientProfile,
                serviceCategory,
                subServiceCategory,
                ClientOrderStatus.SEARCH_MASTER);

        clientOrderRepo.save(serviceRequest);

        return IdDto.builder()
                .id(serviceRequest.getId())
                .build();
    }

    @Override
    public void acceptOrder(UUID orderId) {
        var user = authUtils.getAuthenticatedUser();
        if (user.getRole() != Role.MASTER) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        String.format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.MASTER_PROFILE.get()),
                        HttpStatus.NOT_FOUND
                ));
        var serviceRequest = clientOrderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                        String.format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.CLIENT_ORDER.get()),
                        HttpStatus.NOT_FOUND
                ));

        clientOrderMapper.mapWithMaster(serviceRequest, master);
        clientOrderRepo.save(serviceRequest);
    }
}

