package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.constants.ClientOrderStatus;
import ru.master.service.mapper.EnumMapper;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.mapper.ClientOrderMapper;
import ru.master.service.model.*;
import ru.master.service.model.dto.inner.MasterInfoForOrderDto;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.ListClientOrderDto;
import ru.master.service.model.dto.response.OrderInfoDto;

@Component
@RequiredArgsConstructor
public class ClientOrderMapperImpl implements ClientOrderMapper {

    private final ServiceCategoryMapper serviceCategoryMapper;
    private final EnumMapper enumMapper;

    @Override
    public ClientOrder toEntity(CreateClientOrderDto dto,
                                City city,
                                ClientProfile clientProfile,
                                ServiceCategory serviceCategory,
                                SubServiceCategory subServiceCategory,
                                ClientOrderStatus clientOrderStatus
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
                .clientOrderStatus(clientOrderStatus)
                .city(city)
                .clientProfile(clientProfile)
                .serviceCategory(serviceCategory)
                .subServiceCategory(subServiceCategory)
                .build();
    }

    @Override
    public OrderInfoDto toOrderInfoDto(ClientOrder entity) {
        if (entity == null) return null;


        var request = OrderInfoDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .comment(entity.getComment())
                .preferredDateTime(entity.getPreferredDateTime())
                .urgent(entity.isUrgent())
                .clientRating(entity.getClientRating())
                .clientFeedback(entity.getClientFeedback())
                .serviceType(enumMapper.toDto(entity.getServiceType()))
                .clientOrderStatus(enumMapper.toDto(entity.getClientOrderStatus()))
                .serviceCategoryDto(serviceCategoryMapper.toServiceCategoryForOrderDto(entity))
                .build();

        if (entity.getMasterProfile() != null) {
            var masterInfoDto = MasterInfoForOrderDto.builder()
                    .id(entity.getMasterProfile().getId())
                    .firstName(entity.getMasterProfile().getFirstName())
                    .lastName(entity.getMasterProfile().getLastName())
                    .phoneNumber(entity.getMasterProfile().getUser().getPhoneNumber())
                    .averageRating(entity.getMasterProfile().getAverageRating())
                    .build();

            request.setMasterDto(masterInfoDto);
        }

        return request;
    }

    @Override
    public ListClientOrderDto toListClientOrderDto(ClientOrder clientOrder) {
        if (clientOrder == null) return null;

        return ListClientOrderDto.builder()
                .id(clientOrder.getId())
                .createdAt(clientOrder.getCreatedAt())
                .updatedAt(clientOrder.getUpdatedAt())
                .serviceType(enumMapper.toDto(clientOrder.getServiceType()))
                .clientOrderStatus(enumMapper.toDto(clientOrder.getClientOrderStatus()))
                .serviceCategoryDto(serviceCategoryMapper.toServiceCategoryForOrderDto(clientOrder))
                .build();
    }

    @Override
    public void toCancelOrderForClient(CancelOrderDto reqDto, ClientOrder order) {
        order.setClientOrderStatus(ClientOrderStatus.CANCELLED);
        order.setRejectionReason(reqDto.getRejectionReason());
    }

    @Override
    public void toCompleteOrderForClient(CompleteOrderDto reqDto, ClientOrder order) {
        order.setClientOrderStatus(ClientOrderStatus.COMPLETED);
        order.setClientRating(reqDto.getClientRating());
        order.setClientFeedback(reqDto.getClientFeedback());
    }

    @Override
    public void mapWithMaster(ClientOrder clientOrder, MasterProfile master) {
        clientOrder.setMasterProfile(master);
        clientOrder.setClientOrderStatus(ClientOrderStatus.IN_PROGRESS);
    }
}
