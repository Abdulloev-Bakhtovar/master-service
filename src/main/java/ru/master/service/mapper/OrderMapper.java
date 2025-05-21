package ru.master.service.mapper;

import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.model.*;
import ru.master.service.model.dto.request.CancelOrderForClientDto;
import ru.master.service.model.dto.request.CompleteOrderForClientDto;
import ru.master.service.model.dto.request.CreateOrderReqDto;
import ru.master.service.model.dto.response.*;

import java.util.List;

public interface OrderMapper {

    Order toOrderEntity(CreateOrderReqDto dto,
                        ClientProfile clientProfile,
                        Subservice subservice,
                        ClientOrderStatus clientOrderStatus,
                        MasterOrderStatus masterOrderStatus);

    OrderDetailForClientResDto toOrderDetailResForClientDto(Order order,
                                                            MasterFeedback masterFeedback);

    AllClientOrderResDto toAllClientOrderResDto(Order order);

    void toCancelOrderForClient(CancelOrderForClientDto reqDto, Order order);

    void toCompleteOrderForClient(CompleteOrderForClientDto reqDto, Order order);

    List<MasterAvailableOrdersResDto> toAllAvailableOrderForMasterDto(List<Order> orders);

    MasterCompletedOrdersResDto toMasterCompletedOrdersResDto(Order order);

    MasterActiveOrdersResDto toMasterActiveOrdersResDto(Order order);

    OrderDetailForMasterResDto toOrderDetailForMasterResDto(Order order, MasterFeedback masterFeedback);
}
