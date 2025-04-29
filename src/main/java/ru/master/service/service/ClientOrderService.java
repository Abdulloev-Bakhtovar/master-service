package ru.master.service.service;

import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ClientOrderDto;
import ru.master.service.model.dto.request.ServiceRequestInfoDto;

import java.util.UUID;

public interface ClientOrderService {

    ServiceRequestInfoDto getById(UUID id);

    IdDto create(ClientOrderDto dto);

    void acceptOrder(UUID orderId);
}
