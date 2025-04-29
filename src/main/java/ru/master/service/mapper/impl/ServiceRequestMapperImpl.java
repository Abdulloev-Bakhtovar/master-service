package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.constants.ServiceRequestStatus;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.mapper.ServiceRequestMapper;
import ru.master.service.model.*;
import ru.master.service.model.dto.EnumDto;
import ru.master.service.model.dto.MasterInfoDto;
import ru.master.service.model.dto.ServiceRequestDto;
import ru.master.service.model.dto.SubServiceCategoryDto;
import ru.master.service.model.dto.request.ServiceRequestInfoDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceRequestMapperImpl implements ServiceRequestMapper {

    private final ServiceCategoryMapper serviceCategoryMapper;
    private final SubServiceCategoryMapperImpl subServiceCategoryMapperImpl;

    @Override
    public ServiceRequest toEntity(ServiceRequestDto dto,
                                   City city,
                                   ClientProfile clientProfile,
                                   ServiceCategory serviceCategory,
                                   SubServiceCategory subServiceCategory,
                                   ServiceRequestStatus serviceRequestStatus
    ) {
        if (dto == null) return null;

        return ServiceRequest.builder()
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
                .serviceRequestStatus(serviceRequestStatus)
                .city(city)
                .clientProfile(clientProfile)
                .serviceCategory(serviceCategory)
                .subServiceCategory(subServiceCategory)
                .build();
    }

    @Override
    public ServiceRequestDto toDto(ServiceRequest entity) {
        return null;
    }

    @Override
    public ServiceRequestInfoDto requestInfoDto(ServiceRequest entity) {
        if (entity == null) return null;

        EnumDto serviceRequestStatus = EnumDto.builder()
                .name(entity.getServiceRequestStatus().name())
                .displayName(entity.getServiceRequestStatus().getDisplayName())
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
    public void mapWithMaster(ServiceRequest serviceRequest, MasterProfile master) {
        serviceRequest.setMasterProfile(master);
        serviceRequest.setServiceRequestStatus(ServiceRequestStatus.IN_PROGRESS);
    }
}
