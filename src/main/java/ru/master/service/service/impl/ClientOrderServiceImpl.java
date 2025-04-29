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
import ru.master.service.model.ClientOrder;
import ru.master.service.model.dto.response.IdDto;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.ListClientOrderDto;
import ru.master.service.model.dto.response.OrderInfoDto;
import ru.master.service.repository.*;
import ru.master.service.service.ClientOrderService;
import ru.master.service.service.MasterProfileService;
import ru.master.service.util.AuthUtils;

import java.util.ArrayList;
import java.util.List;
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
    private final MasterProfileService masterProfileService;

    @Override
    @Transactional(readOnly = true)
    public OrderInfoDto getById(UUID id) {
        var entity = clientOrderRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        String.format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.CLIENT_ORDER.get()),
                        HttpStatus.NOT_FOUND
                ));

        return clientOrderMapper.toOrderInfoDto(entity);
    }

    @Override
    public IdDto create(CreateClientOrderDto reqDto) {

        var user = authUtils.getAuthenticatedUser();

        var city = cityRepo.findById(reqDto.getCityDto().getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var clientProfile = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var serviceCategory = serviceCategoryRepo.findById(reqDto.getServiceCategoryDto().getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.SERVICE_CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var subServiceCategory = subServiceCategoryRepo.findById(reqDto.getServiceCategoryDto()
                        .getSubServiceCategoryDto()
                        .getId()
                )
                .orElseThrow(() -> new AppException(
                        ErrorMessage.SUB_SERVICE_CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        if (!serviceCategory.getSubServices().contains(subServiceCategory)) {
            throw new AppException(
                    "Subservice does not belong to selected service",
                    HttpStatus.BAD_REQUEST
            );
        }

        var serviceRequest = clientOrderMapper.toEntity(
                reqDto,
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
        var order = clientOrderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                        String.format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.CLIENT_ORDER.get()),
                        HttpStatus.NOT_FOUND
                ));

        clientOrderMapper.mapWithMaster(order, master);
        clientOrderRepo.save(order);
    }

    @Override
    public List<ListClientOrderDto> getClientOrders() {
        var user = authUtils.getAuthenticatedUser();

        if (!user.getRole().equals(Role.CLIENT)) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var client = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var orders = clientOrderRepo.findAllByClientProfileId(client.getId())
                .orElse(new ArrayList<>());

        return orders.stream()
                .map(clientOrderMapper::toListClientOrderDto)
                .toList();
    }

    @Override
    public void cancelOrderForClient(CancelOrderDto reqDto) {
        var order = getClientOrder(reqDto.getId());
        clientOrderMapper.toCancelOrderForClient(reqDto, order);
        clientOrderRepo.save(order);
    }

    @Override
    public void completeOrderForClient(CompleteOrderDto reqDto) {
        var order = getClientOrder(reqDto.getId());
        clientOrderMapper.toCompleteOrderForClient(reqDto, order);
        masterProfileService.updateMasterAverageRating(order.getMasterProfile(), reqDto.getClientRating());
        clientOrderRepo.save(order);
    }

    private ClientOrder getClientOrder(UUID reqDto) {
        var user = authUtils.getAuthenticatedUser();

        if (!user.getRole().equals(Role.CLIENT)) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var client = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var order = clientOrderRepo.findById(reqDto)
                .orElseThrow(() -> new AppException(
                        String.format(ErrorMessage.ENTITY_NOT_FOUND, EntityName.CLIENT_ORDER.get()),
                        HttpStatus.NOT_FOUND
                ));

        if (client.getId() != order.getClientProfile().getId()) {
            throw new AppException(
                    ErrorMessage.ORDER_ACCESS_DENIED,
                    HttpStatus.FORBIDDEN
            );
        }
        return order;
    }
}

