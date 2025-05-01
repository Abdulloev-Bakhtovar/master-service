package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.constant.Role;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.ClientOrderMapper;
import ru.master.service.model.ClientOrder;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.*;
import ru.master.service.repository.*;
import ru.master.service.service.ClientOrderService;
import ru.master.service.service.MasterFeedbackService;
import ru.master.service.service.MasterProfileService;
import ru.master.service.util.AuthUtil;

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
    private final AuthUtil authUtil;
    private final MasterFeedbackRepo masterFeedbackRepo;
    private final MasterProfileRepo masterProfileRepo;
    private final MasterProfileService masterProfileService;
    private final MasterFeedbackService masterFeedbackService;

    @Override
    public IdDto create(CreateClientOrderDto reqDto) {

        var serviceRequest = getClientOrder(reqDto);
        clientOrderRepo.save(serviceRequest);

        return IdDto.builder()
                .id(serviceRequest.getId())
                .build();
    }

    @Override
    public OrderInfoForClientDto getOrderInfoForClient(UUID id) {
        var user = authUtil.getAuthenticatedUser();

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
        var order = clientOrderRepo.findByIdAndClientProfileId(id, client.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var masterFeedback = masterFeedbackRepo.findByOrderId(order.getId())
                .orElse(null);

        return clientOrderMapper.toOrderInfoForClientDto(order, masterFeedback);
    }

    @Override
    public OrderInfoForMasterDto getOrderInfoForMaster(UUID id) {
        var user = authUtil.getAuthenticatedUser();

        if (!user.getRole().equals(Role.MASTER)) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var order = clientOrderRepo.findByIdAndMasterProfileId(id, master.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var masterFeedback = masterFeedbackRepo.findByOrderId(order.getId())
                .orElse(null);

        return clientOrderMapper.toOrderInfoForMasterDto(order, masterFeedback);
    }

    @Override
    public List<AllClientProfileOrderDto> getMyOrdersForClientProfile() {
        var user = authUtil.getAuthenticatedUser();

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
                .map(clientOrderMapper::toAllClientProfileOrderDto)
                .toList();
    }

    @Override
    public void acceptOrderForMaster(UUID orderId) {
        var user = authUtil.getAuthenticatedUser();
        if (user.getRole() != Role.MASTER) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var order = clientOrderRepo.findById(orderId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        if (order.getClientOrderStatus() == ClientOrderStatus.TAKEN_IN_WORK) {
            throw new AppException(
                    ErrorMessage.ORDER_TAKEN,
                    HttpStatus.NOT_FOUND
            );
        }

        clientOrderMapper.mapWithMaster(order, master);
        clientOrderRepo.save(order);
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
        masterFeedbackService.create(reqDto.getMasterFeedback(), order);
        masterProfileService.updateMasterAverageRating(
                order.getMasterProfile(),
                reqDto.getMasterFeedback().getRating()
        );
        clientOrderRepo.save(order);
    }

    @Override
    public List<AvailableOrdersForMasterDto> getAvailableOrdersForMaster() {
        var user = authUtil.getAuthenticatedUser();
        if (user.getRole() != Role.MASTER) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var orders = clientOrderRepo.findAllByCityId(master.getCity().getId())
                .orElse(new ArrayList<>());

        return clientOrderMapper.toAvailableOrdersForMasterDto(orders);
    }

    @Override
    public List<AllMasterProfileOrderDto> getMasterCompletedOrders() {
        var user = authUtil.getAuthenticatedUser();
        if (user.getRole() != Role.MASTER) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var orders = clientOrderRepo.findAllByMasterProfileIdAndMasterOrderStatus(
                master.getId(), MasterOrderStatus.FINISHED
                )
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return orders.stream()
                .map(clientOrderMapper::toAllMasterProfileOrderDto)
                .toList();
    }

    @Override
    public List<AllMasterProfileOrderDto> getMasterActiveOrders() {
        var user = authUtil.getAuthenticatedUser();
        if (user.getRole() != Role.MASTER) {
            throw new AppException(
                    ErrorMessage.INVALID_ROLE_FOR_OPERATION,
                    HttpStatus.FORBIDDEN
            );
        }
        var master = masterProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));
        var orders = clientOrderRepo.findAllByMasterProfileIdAndMasterOrderStatus(
                        master.getId(), MasterOrderStatus.TAKEN_IN_WORK
                )
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ORDER_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        return orders.stream()
                .map(clientOrderMapper::toAllMasterProfileOrderDto)
                .toList();
    }

    private ClientOrder getClientOrder(UUID reqDto) {
        var user = authUtil.getAuthenticatedUser();

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
                        ErrorMessage.ORDER_NOT_FOUND,
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

    private ClientOrder getClientOrder(CreateClientOrderDto reqDto) {
        var user = authUtil.getAuthenticatedUser();

        var city = cityRepo.findById(reqDto.getCityId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var clientProfile = clientProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CLIENT_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var serviceCategory = serviceCategoryRepo.findById(reqDto.getServiceCategoryId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.SERVICE_CATEGORY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var subServiceCategory = subServiceCategoryRepo.findById(reqDto.getSubServiceCategoryId())
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

        return clientOrderMapper.toEntity(
                reqDto,
                city,
                clientProfile,
                serviceCategory,
                subServiceCategory,
                ClientOrderStatus.SEARCHING_FOR_MASTER);
    }
}
