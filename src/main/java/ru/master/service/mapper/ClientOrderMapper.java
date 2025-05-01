package ru.master.service.mapper;

import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.model.*;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.*;

import java.util.List;

public interface ClientOrderMapper {

    ClientOrder toEntity(CreateClientOrderDto dto,
                         City city,
                         ClientProfile clientProfile,
                         ServiceCategory serviceCategory,
                         SubServiceCategory subServiceCategory,
                         ClientOrderStatus clientOrderStatus);

    OrderInfoForClientDto toOrderInfoForClientDto(ClientOrder order, MasterFeedback masterFeedback);

    OrderInfoForMasterDto toOrderInfoForMasterDto(ClientOrder order, MasterFeedback masterFeedback);

    AllClientProfileOrderDto toAllClientProfileOrderDto(ClientOrder clientOrder);

    void mapWithMaster(ClientOrder order, MasterProfile master);

    void toCancelOrderForClient(CancelOrderDto reqDto, ClientOrder order);

    void toCompleteOrderForClient(CompleteOrderDto reqDto, ClientOrder order);

    List<AvailableOrdersForMasterDto> toAvailableOrdersForMasterDto(List<ClientOrder> orders);

    AllMasterProfileOrderDto toAllMasterProfileOrderDto(ClientOrder clientOrder);
}
