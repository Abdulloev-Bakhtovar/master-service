package ru.master.service.service;

import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ServiceRequestDto;
import ru.master.service.model.dto.request.ServiceRequestInfoDto;

import java.util.UUID;

public interface ServiceRequestService {

    ServiceRequestInfoDto getById(UUID id);

    IdDto create(ServiceRequestDto dto);
}
