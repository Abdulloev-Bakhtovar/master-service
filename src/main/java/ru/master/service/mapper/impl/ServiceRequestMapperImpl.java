package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.constants.ServiceRequestStatus;
import ru.master.service.mapper.ServiceRequestMapper;
import ru.master.service.model.*;
import ru.master.service.model.dto.ServiceRequestDto;

@Component
public class ServiceRequestMapperImpl implements ServiceRequestMapper {

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
}
