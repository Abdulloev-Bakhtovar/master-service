package ru.master.service.service;

import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface ClientOrderService {

    IdDto create(CreateClientOrderDto reqDto);

    OrderInfoForClientDto getOrderInfoForClient(UUID id);

    OrderInfoForMasterDto getOrderInfoForMaster(UUID id);

    List<AllClientProfileOrderDto> getMyOrdersForClientProfile();

    void acceptOrderForMaster(UUID orderId);

    void cancelOrderForClient(CancelOrderDto reqDto);

    void completeOrderForClient(CompleteOrderDto reqDto);

    List<AvailableOrdersForMasterDto> getAvailableOrdersForMaster();

    List<AllMasterProfileOrderDto> getMasterCompletedOrders();

    List<AllMasterProfileOrderDto> getMasterActiveOrders();
}
