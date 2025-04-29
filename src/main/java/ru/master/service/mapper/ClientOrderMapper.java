package ru.master.service.mapper;

import ru.master.service.constants.ClientOrderStatus;
import ru.master.service.model.*;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.ListClientOrderDto;
import ru.master.service.model.dto.response.OrderInfoDto;

public interface ClientOrderMapper {

    ClientOrder toEntity(CreateClientOrderDto dto,
                         City city,
                         ClientProfile clientProfile,
                         ServiceCategory serviceCategory,
                         SubServiceCategory subServiceCategory,
                         ClientOrderStatus clientOrderStatus);

    void mapWithMaster(ClientOrder clientOrder, MasterProfile master);

    OrderInfoDto toOrderInfoDto(ClientOrder entity);

    ListClientOrderDto toListClientOrderDto(ClientOrder clientOrder);

    void toCancelOrderForClient(CancelOrderDto reqDto, ClientOrder order);

    void toCompleteOrderForClient(CompleteOrderDto reqDto, ClientOrder order);
}
