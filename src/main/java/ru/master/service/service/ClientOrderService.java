package ru.master.service.service;

import ru.master.service.model.dto.response.IdDto;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.ListClientOrderDto;
import ru.master.service.model.dto.response.OrderInfoDto;

import java.util.List;
import java.util.UUID;

public interface ClientOrderService {

    OrderInfoDto getById(UUID id);

    IdDto create(CreateClientOrderDto dto);

    void acceptOrder(UUID orderId);

    List<ListClientOrderDto> getClientOrders();

    void cancelOrderForClient(CancelOrderDto reqDto);

    void completeOrderForClient(CompleteOrderDto reqDto);
}
