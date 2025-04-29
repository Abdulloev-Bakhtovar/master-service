package ru.master.service.service;

import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ClientOrderDto;
import ru.master.service.model.dto.request.ClientOrderInfoDto;

import java.util.UUID;

public interface ClientOrderService {



    ClientOrderInfoDto getById(UUID id);

    IdDto create(ClientOrderDto dto);

    void acceptOrder(UUID orderId);
}
