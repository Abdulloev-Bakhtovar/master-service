package ru.master.service.mapper;

import ru.master.service.constants.ServiceRequestStatus;
import ru.master.service.model.*;
import ru.master.service.model.dto.ServiceRequestDto;

public interface ServiceRequestMapper {

    ServiceRequest toEntity(ServiceRequestDto dto,
                            City city,
                            ClientProfile clientProfile,
                            ServiceCategory serviceCategory,
                            SubServiceCategory subServiceCategory,
                            ServiceRequestStatus serviceRequestStatus);
}
