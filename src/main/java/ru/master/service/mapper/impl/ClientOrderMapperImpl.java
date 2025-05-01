package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.mapper.*;
import ru.master.service.model.*;
import ru.master.service.model.dto.ClientInfoForMasterDto;
import ru.master.service.model.dto.ServiceCategoryForAvailableOrdersDto;
import ru.master.service.model.dto.SubServiceForAvailableOrdersDto;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientOrderMapperImpl implements ClientOrderMapper {

    private final EnumMapper enumMapper;
    private final MasterFeedbackMapper masterFeedbackMapper;
    private final MasterProfileMapper masterProfileMapper;
    private final ServiceCategoryMapper serviceCategoryMapper;

    @Override
    public ClientOrder toEntity(CreateClientOrderDto dto,
                                City city,
                                ClientProfile clientProfile,
                                ServiceCategory serviceCategory,
                                SubServiceCategory subServiceCategory,
                                ClientOrderStatus orderStatus
    ) {
        if (dto == null) return null;

        return ClientOrder.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .comment(dto.getComment())
                .preferredDateTime(dto.getPreferredDateTime())
                .urgent(dto.isUrgent())
                .agreeToTerms(dto.isAgreeToTerms())
                .price(dto.getPrice())
                .serviceType(dto.getServiceType())
                .clientOrderStatus(orderStatus)
                .city(city)
                .clientProfile(clientProfile)
                .serviceCategory(serviceCategory)
                .subServiceCategory(subServiceCategory)
                .build();
    }

    @Override
    public OrderInfoForClientDto toOrderInfoForClientDto(ClientOrder entity, MasterFeedback masterFeedback) {
        if (entity == null) return null;

        var request = OrderInfoForClientDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .comment(entity.getComment())
                .preferredDateTime(entity.getPreferredDateTime())
                .urgent(entity.isUrgent())
                .serviceType(enumMapper.toDto(entity.getServiceType()))
                .clientOrderStatus(enumMapper.toDto(entity.getClientOrderStatus()))
                .masterOrderStatus(enumMapper.toDto(entity.getMasterOrderStatus()))
                .serviceCategoryDto(serviceCategoryMapper.toOrderInfoForClientDto(entity))
                .build();

        if (masterFeedback != null) {
            request.setMasterFeedbackDto(masterFeedbackMapper.toOrderInfoForClientDto(masterFeedback));
        }
        if (entity.getMasterProfile() != null) {
            request.setMasterDto(masterProfileMapper.toOrderInfoForClientDto(entity.getMasterProfile()));
        }

        return request;
    }

    @Override
    public OrderInfoForMasterDto toOrderInfoForMasterDto(ClientOrder entity, MasterFeedback masterFeedback) {
        if (entity == null) return null;

        ClientInfoForMasterDto client = ClientInfoForMasterDto.builder()
                .id(entity.getClientProfile().getId())
                .fistName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();

        var request = OrderInfoForMasterDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .comment(entity.getComment())
                .preferredDateTime(entity.getPreferredDateTime())
                .urgent(entity.isUrgent())
                .clientInfoDto(client)
                .serviceType(enumMapper.toDto(entity.getServiceType()))
                .clientOrderStatus(enumMapper.toDto(entity.getClientOrderStatus()))
                .masterOrderStatus(enumMapper.toDto(entity.getMasterOrderStatus()))
                .serviceCategoryDto(serviceCategoryMapper.toOrderInfoForClientDto(entity))
                .build();

        if (masterFeedback != null) {
            request.setMasterFeedbackDto(masterFeedbackMapper.toOrderInfoForClientDto(masterFeedback));
        }

        return request;
    }

    @Override
    public AllClientProfileOrderDto toAllClientProfileOrderDto(ClientOrder clientOrder) {
        if (clientOrder == null) return null;

        return AllClientProfileOrderDto.builder()
                .id(clientOrder.getId())
                .createdAt(clientOrder.getCreatedAt())
                .updatedAt(clientOrder.getUpdatedAt())
                .serviceType(enumMapper.toDto(clientOrder.getServiceType()))
                .clientOrderStatus(enumMapper.toDto(clientOrder.getClientOrderStatus()))
                .clientOrderStatus(enumMapper.toDto(clientOrder.getClientOrderStatus()))
                .masterOrderStatus(enumMapper.toDto(clientOrder.getMasterOrderStatus()))
                .serviceCategoryDto(serviceCategoryMapper.toOrderInfoForClientDto(clientOrder))
                .build();
    }

    @Override
    public void mapWithMaster(ClientOrder order, MasterProfile master) {
        order.setMasterProfile(master);
        order.setClientOrderStatus(ClientOrderStatus.TAKEN_IN_WORK);
        order.setMasterOrderStatus(MasterOrderStatus.TAKEN_IN_WORK);
    }

    @Override
    public void toCancelOrderForClient(CancelOrderDto reqDto, ClientOrder order) {
        order.setClientOrderStatus(ClientOrderStatus.CANCELLED);
        order.setMasterOrderStatus(MasterOrderStatus.CANCELLED);
        order.setRejectionReason(reqDto.getRejectionReason());
    }

    @Override
    public void toCompleteOrderForClient(CompleteOrderDto reqDto, ClientOrder order) {
        order.setClientOrderStatus(ClientOrderStatus.COMPLETED);
    }

    @Override
    public List<AvailableOrdersForMasterDto> toAvailableOrdersForMasterDto(List<ClientOrder> orders) {
        if (orders == null) return new ArrayList<>();

        return orders.stream()
                .map(this::toAvailableOrdersForMasterDto)
                .toList();
    }

    @Override
    public AllMasterProfileOrderDto toAllMasterProfileOrderDto(ClientOrder clientOrder) {
        if (clientOrder == null) return null;

        return AllMasterProfileOrderDto.builder()
                .id(clientOrder.getId())
                .createdAt(clientOrder.getCreatedAt())
                .updatedAt(clientOrder.getUpdatedAt())
                .address(clientOrder.getAddress())
                .serviceType(enumMapper.toDto(clientOrder.getServiceType()))
                .clientOrderStatus(enumMapper.toDto(clientOrder.getClientOrderStatus()))
                .masterOrderStatus(enumMapper.toDto(clientOrder.getMasterOrderStatus()))
                .serviceCategoryDto(serviceCategoryMapper.toOrderInfoForClientDto(clientOrder))
                .build();
    }

    private AvailableOrdersForMasterDto toAvailableOrdersForMasterDto(ClientOrder order) {
        if (order == null) return null;

        SubServiceForAvailableOrdersDto sub = SubServiceForAvailableOrdersDto.builder()
                .id(order.getSubServiceCategory().getId())
                .createdAt(order.getSubServiceCategory().getCreatedAt())
                .updatedAt(order.getSubServiceCategory().getUpdatedAt())
                .name(order.getSubServiceCategory().getName())
                .build();

        ServiceCategoryForAvailableOrdersDto sc = ServiceCategoryForAvailableOrdersDto.builder()
                .id(order.getServiceCategory().getId())
                .createdAt(order.getServiceCategory().getCreatedAt())
                .updatedAt(order.getServiceCategory().getUpdatedAt())
                .name(order.getServiceCategory().getName())
                .subServiceCategory(sub)
                .build();

        return AvailableOrdersForMasterDto.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .serviceType(enumMapper.toDto(order.getServiceType()))
                .clientOrderStatus(enumMapper.toDto(order.getClientOrderStatus()))
                .masterOrderStatus(enumMapper.toDto(order.getMasterOrderStatus()))
                .serviceCategoryDto(sc)
                .build();
    }
}
