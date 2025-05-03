package ru.master.service.service;

import ru.master.service.model.dto.request.CancelOrderForClientDto;
import ru.master.service.model.dto.request.CompleteOrderForClientDto;
import ru.master.service.model.dto.request.CreateOrderReqDto;
import ru.master.service.model.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    IdDto create(CreateOrderReqDto reqDto);

    OrderDetailForClientResDto getByIdForClient(UUID orderId);

    List<AllClientOrderResDto> getAllClientOrders();

    void cancelOrderForClient(UUID orderId, CancelOrderForClientDto reqDto);

    void completeOrderForClient(UUID orderId, CompleteOrderForClientDto reqDto);

    List<MasterAvailableOrdersResDto> getMasterAvailableOrders();

    List<MasterCompletedOrdersResDto> getMasterCompletedOrders();

    List<MasterActiveOrdersResDto> getMasterActiveOrders();

    OrderDetailForMasterResDto getByIdForMaster(UUID orderId);
}
