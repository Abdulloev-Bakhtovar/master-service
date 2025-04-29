package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.constants.ClientOrderStatus;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.mapper.ClientOrderMapper;
import ru.master.service.model.*;
import ru.master.service.model.dto.EnumDto;
import ru.master.service.model.dto.MasterInfoDto;
import ru.master.service.model.dto.ClientOrderDto;
import ru.master.service.model.dto.request.ServiceRequestInfoDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientOrderMapperImpl implements ClientOrderMapper {

    private final ServiceCategoryMapper serviceCategoryMapper;
    private final SubServiceCategoryMapperImpl subServiceCategoryMapperImpl;

    @Override
    public ClientOrder toEntity(ClientOrderDto dto,
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
    public ClientOrderDto toDto(ClientOrder entity) {
        return null;
    }

    @Override
    public ServiceRequestInfoDto orderInfoDto(ClientOrder entity) {
        if (entity == null) return null;

        EnumDto serviceRequestStatus = EnumDto.builder()
                .name(entity.getClientOrderStatus().name())
                .displayName(entity.getClientOrderStatus().getDisplayName())
                .build();

        EnumDto serviceType = EnumDto.builder()
                .name(entity.getServiceType().name())
                .displayName(entity.getServiceType().getDisplayName())
                .build();


        var request = ServiceRequestInfoDto.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .phoneNumber(entity.getPhoneNumber())
                .comment(entity.getComment())
                .preferredDateTime(entity.getPreferredDateTime())
                .urgent(entity.isUrgent())
                .serviceType(serviceType)
                .serviceRequestStatus(serviceRequestStatus)
                .serviceCategory(serviceCategoryMapper.toDto(entity.getServiceCategory()))
                .build();

        if (entity.getMasterProfile() != null) {
            MasterInfoDto masterInfoDto = MasterInfoDto.builder()
                    .id(entity.getMasterProfile().getId())
                    .firstName(entity.getMasterProfile().getFirstName())
                    .lastName(entity.getMasterProfile().getLastName())
                    .phoneNumber(entity.getMasterProfile().getUser().getPhoneNumber())
                    .rating(entity.getMasterProfile().getRating())
                    .build();

            request.setMasterInfoDto(masterInfoDto);
        }

        List<SubServiceCategory> subServiceCategories = new ArrayList<>();
        subServiceCategories.add(entity.getSubServiceCategory());
        request.getServiceCategory().setSubServiceCategoryDtos(subServiceCategoryMapperImpl.toDtoList(subServiceCategories));

        return request;
    }

    @Override
    public void mapWithMaster(ClientOrder clientOrder, MasterProfile master) {
        clientOrder.setMasterProfile(master);
        clientOrder.setClientOrderStatus(ClientOrderStatus.IN_PROGRESS);
    }
}
