package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.constant.MasterStatus;
import ru.master.service.mapper.OrderMapper;
import ru.master.service.model.*;
import ru.master.service.model.dto.*;
import ru.master.service.model.dto.request.CancelOrderForClientDto;
import ru.master.service.model.dto.request.CompleteOrderForClientDto;
import ru.master.service.model.dto.request.CreateOrderReqDto;
import ru.master.service.model.dto.response.*;

import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toOrderEntity(CreateOrderReqDto dto,
                               ClientProfile clientProfile,
                               Subservice subservice,
                               ClientOrderStatus clientOrderStatus,
                               MasterOrderStatus masterOrderStatus) {
        if (dto == null) return null;


        return Order.builder()
                .firstName(clientProfile.getFirstName())
                .lastName(clientProfile.getLastName())
                .city(clientProfile.getCity())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .comment(dto.getComment())
                .preferredDateTime(dto.getPreferredDateTime())
                .urgent(dto.isUrgent())
                .agreeToTerms(dto.isAgreeToTerms())
                .price(dto.getPrice().setScale(2, RoundingMode.HALF_UP))
                .serviceType(dto.getServiceType())
                .clientOrderStatus(clientOrderStatus)
                .masterOrderStatus(masterOrderStatus)
                .clientProfile(clientProfile)
                .subservice(subservice)
                .paymentMethod(dto.getPaymentMethod())
                .build();
    }

    @Override
    public OrderDetailForClientResDto toOrderDetailResForClientDto(Order entity, MasterFeedback masterFeedback) {
        if (entity == null) return null;

        var serviceType = EnumResDto.builder()
                .name(entity.getServiceType().name())
                .displayName(entity.getServiceType().getDisplayName())
                .build();

        var clientOrderStatus = EnumResDto.builder()
                .name(entity.getClientOrderStatus().name())
                .displayName(entity.getClientOrderStatus().getDisplayName())
                .build();

        var subserviceDto = SubserviceForClientOrderDetailDto.builder()
                .id(entity.getSubservice().getId())
                .name(entity.getSubservice().getName())
                .createdAt(entity.getSubservice().getCreatedAt())
                .updatedAt(entity.getSubservice().getUpdatedAt())
                .build();

        var serviceCategoryDto = ServiceCategoryForClientOrderDetailDto.builder()
                .id(entity.getSubservice().getServiceCategory().getId())
                .name(entity.getSubservice().getServiceCategory().getName())
                .createdAt(entity.getSubservice().getServiceCategory().getCreatedAt())
                .updatedAt(entity.getSubservice().getServiceCategory().getUpdatedAt())
                .subserviceDto(subserviceDto)
                .build();

        var request = OrderDetailForClientResDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .comment(entity.getComment())
                .preferredDateTime(entity.getPreferredDateTime())
                .urgent(entity.isUrgent())
                .serviceType(serviceType)
                .clientOrderStatus(clientOrderStatus)
                .serviceCategoryDto(serviceCategoryDto)
                .paymentMethod(entity.getPaymentMethod())
                .build();

        if (masterFeedback != null) {
            var masterFeedbackDto = MasterFeedbackForClientOrderDetailDto.builder()
                    .id(masterFeedback.getId())
                    .rating(masterFeedback.getRating())
                    .review(masterFeedback.getReview())
                    .createdAt(masterFeedback.getCreatedAt())
                    .updatedAt(masterFeedback.getUpdatedAt())
                    .build();
            request.setMasterFeedbackDto(masterFeedbackDto);
        }
        if (entity.getMasterProfile() != null) {

            var masterInfoDto = MasterInfoForClientOrderDetailDto.builder()
                    .id(entity.getMasterProfile().getId())
                    .firstName(entity.getMasterProfile().getFirstName())
                    .lastName(entity.getMasterProfile().getLastName())
                    .averageRating(entity.getMasterProfile().getAverageRating())
                    .phoneNumber(entity.getMasterProfile().getUser().getPhoneNumber())
                    .build();

            request.setMasterInfoDto(masterInfoDto);
        }

        return request;
    }

    @Override
    public AllClientOrderResDto toAllClientOrderResDto(Order entity) {
        if (entity == null) return null;

        var serviceType = EnumResDto.builder()
                .name(entity.getServiceType().name())
                .displayName(entity.getServiceType().getDisplayName())
                .build();

        var clientOrderStatus = EnumResDto.builder()
                .name(entity.getClientOrderStatus().name())
                .displayName(entity.getClientOrderStatus().getDisplayName())
                .build();

        var subserviceDto = SubserviceForAllClientOrderDto.builder()
                .id(entity.getSubservice().getId())
                .name(entity.getSubservice().getName())
                .createdAt(entity.getSubservice().getCreatedAt())
                .updatedAt(entity.getSubservice().getUpdatedAt())
                .build();

        var serviceCategoryDto = ServiceCategoryForAllClientOrderDto.builder()
                .id(entity.getSubservice().getServiceCategory().getId())
                .name(entity.getSubservice().getServiceCategory().getName())
                .createdAt(entity.getSubservice().getServiceCategory().getCreatedAt())
                .updatedAt(entity.getSubservice().getServiceCategory().getUpdatedAt())
                .subserviceDto(subserviceDto)
                .build();

        return AllClientOrderResDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .serviceType(serviceType)
                .clientOrderStatus(clientOrderStatus)
                .serviceCategoryDto(serviceCategoryDto)
                .build();
    }

    @Override
    public void toCancelOrderForClient(CancelOrderForClientDto reqDto, Order order) {
        order.setClientOrderStatus(ClientOrderStatus.CANCELLED);
        order.setMasterOrderStatus(MasterOrderStatus.CANCELLED);
        order.setRejectionReason(reqDto.getRejectionReason());

        if (order.getMasterProfile() != null) {
            order.getMasterProfile().setMasterStatus(MasterStatus.WAITING_FOR_ORDERS);
        }
    }

    @Override
    public void toCompleteOrderForClient(CompleteOrderForClientDto reqDto, Order order) {
        order.setClientOrderStatus(ClientOrderStatus.COMPLETED);
        order.setMasterOrderStatus(MasterOrderStatus.COMPLETED);
        order.setClosedAt(Instant.now());

        if (order.getMasterProfile() != null) {
            order.getMasterProfile().setMasterStatus(MasterStatus.WAITING_FOR_ORDERS);
        }
    }

    @Override
    public List<MasterAvailableOrdersResDto> toAllAvailableOrderForMasterDto(List<Order> orders) {
        if (orders == null) return new ArrayList<>();

        return orders.stream()
                .map(this::toAllAvailableOrderForMasterDto)
                .toList();
    }

    @Override
    public MasterCompletedOrdersResDto toMasterCompletedOrdersResDto(Order entity) {
        if (entity == null) return null;

        var serviceType = EnumResDto.builder()
                .name(entity.getServiceType().name())
                .displayName(entity.getServiceType().getDisplayName())
                .build();

        var masterOrderStatus = EnumResDto.builder()
                .name(entity.getMasterOrderStatus().name())
                .displayName(entity.getMasterOrderStatus().getDisplayName())
                .build();

        var subserviceDto = SubserviceForMasterCompletedOrdersDto.builder()
                .id(entity.getSubservice().getId())
                .name(entity.getSubservice().getName())
                .createdAt(entity.getSubservice().getCreatedAt())
                .updatedAt(entity.getSubservice().getUpdatedAt())
                .build();

        var serviceCategoryDto = ServiceCategoryForMasterCompletedOrdersDto.builder()
                .id(entity.getSubservice().getServiceCategory().getId())
                .name(entity.getSubservice().getServiceCategory().getName())
                .createdAt(entity.getSubservice().getServiceCategory().getCreatedAt())
                .updatedAt(entity.getSubservice().getServiceCategory().getUpdatedAt())
                .subserviceDto(subserviceDto)
                .build();

        return MasterCompletedOrdersResDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .address(entity.getAddress())
                .serviceType(serviceType)
                .masterOrderStatus(masterOrderStatus)
                .serviceCategoryDto(serviceCategoryDto)
                .build();
    }

    @Override
    public MasterActiveOrdersResDto toMasterActiveOrdersResDto(Order entity) {
        if (entity == null) return null;

        var serviceType = EnumResDto.builder()
                .name(entity.getServiceType().name())
                .displayName(entity.getServiceType().getDisplayName())
                .build();

        var masterOrderStatus = EnumResDto.builder()
                .name(entity.getMasterOrderStatus().name())
                .displayName(entity.getMasterOrderStatus().getDisplayName())
                .build();

        var subserviceDto = SubserviceForMasterActiveOrdersDto.builder()
                .id(entity.getSubservice().getId())
                .name(entity.getSubservice().getName())
                .createdAt(entity.getSubservice().getCreatedAt())
                .updatedAt(entity.getSubservice().getUpdatedAt())
                .build();

        var serviceCategoryDto = ServiceCategoryForMasterActiveOrdersDto.builder()
                .id(entity.getSubservice().getServiceCategory().getId())
                .name(entity.getSubservice().getServiceCategory().getName())
                .createdAt(entity.getSubservice().getServiceCategory().getCreatedAt())
                .updatedAt(entity.getSubservice().getServiceCategory().getUpdatedAt())
                .subserviceDto(subserviceDto)
                .build();

        return MasterActiveOrdersResDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .address(entity.getAddress())
                .serviceType(serviceType)
                .masterOrderStatus(masterOrderStatus)
                .serviceCategoryDto(serviceCategoryDto)
                .build();
    }

    @Override
    public OrderDetailForMasterResDto toOrderDetailForMasterResDto(Order entity, MasterFeedback masterFeedback) {
        if (entity == null) return null;

        var serviceType = EnumResDto.builder()
                .name(entity.getServiceType().name())
                .displayName(entity.getServiceType().getDisplayName())
                .build();

        var masterOrderStatus = EnumResDto.builder()
                .name(entity.getMasterOrderStatus().name())
                .displayName(entity.getMasterOrderStatus().getDisplayName())
                .build();

        var subserviceDto = SubserviceForMasterOrderDetailDto.builder()
                .id(entity.getSubservice().getId())
                .name(entity.getSubservice().getName())
                .createdAt(entity.getSubservice().getCreatedAt())
                .updatedAt(entity.getSubservice().getUpdatedAt())
                .build();

        var serviceCategoryDto = ServiceCategoryForMasterOrderDetailDto.builder()
                .id(entity.getSubservice().getServiceCategory().getId())
                .name(entity.getSubservice().getServiceCategory().getName())
                .createdAt(entity.getSubservice().getServiceCategory().getCreatedAt())
                .updatedAt(entity.getSubservice().getServiceCategory().getUpdatedAt())
                .subserviceDto(subserviceDto)
                .build();

        var clientInfoDto = ClientInfoForMasterOrderDetailDto.builder()
                .id(entity.getClientProfile().getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .build();

        var request = OrderDetailForMasterResDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .comment(entity.getComment())
                .preferredDateTime(entity.getPreferredDateTime())
                .urgent(entity.isUrgent())
                .serviceType(serviceType)
                .masterOrderStatus(masterOrderStatus)
                .serviceCategoryDto(serviceCategoryDto)
                .clientInfoDto(clientInfoDto)
                .build();

        if (masterFeedback != null) {
            var masterFeedbackDto = MasterFeedbackForMasterOrderDetailDto.builder()
                    .id(masterFeedback.getId())
                    .rating(masterFeedback.getRating())
                    .review(masterFeedback.getReview())
                    .createdAt(masterFeedback.getCreatedAt())
                    .updatedAt(masterFeedback.getUpdatedAt())
                    .build();
            request.setMasterFeedbackDto(masterFeedbackDto);
        }

        return request;
    }

    private MasterAvailableOrdersResDto toAllAvailableOrderForMasterDto(Order entity) {
        if (entity == null) return null;

        var serviceType = EnumResDto.builder()
                .name(entity.getServiceType().name())
                .displayName(entity.getServiceType().getDisplayName())
                .build();

        var clientOrderStatus = EnumResDto.builder()
                .name(entity.getClientOrderStatus().name())
                .displayName(entity.getClientOrderStatus().getDisplayName())
                .build();

        var masterOrderStatus = EnumResDto.builder()
                .name(entity.getMasterOrderStatus().name())
                .displayName(entity.getMasterOrderStatus().getDisplayName())
                .build();

        var subserviceDto = SubserviceForAvailableOrdersDto.builder()
                .id(entity.getSubservice().getId())
                .name(entity.getSubservice().getName())
                .createdAt(entity.getSubservice().getCreatedAt())
                .updatedAt(entity.getSubservice().getUpdatedAt())
                .build();

        var serviceCategoryDto = ServiceCategoryForAvailableOrdersDto.builder()
                .id(entity.getSubservice().getServiceCategory().getId())
                .name(entity.getSubservice().getServiceCategory().getName())
                .createdAt(entity.getSubservice().getServiceCategory().getCreatedAt())
                .updatedAt(entity.getSubservice().getServiceCategory().getUpdatedAt())
                .subserviceDto(subserviceDto)
                .build();

        return MasterAvailableOrdersResDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .address(entity.getAddress())
                .serviceType(serviceType)
                .clientOrderStatus(clientOrderStatus)
                .masterOrderStatus(masterOrderStatus)
                .serviceCategoryDto(serviceCategoryDto)
                .price(entity.getPrice())
                .build();
    }
}
