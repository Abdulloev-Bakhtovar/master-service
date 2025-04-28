package ru.master.service.service;

import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ServiceRequestDto;

import java.util.UUID;

public interface ServiceRequestService {

    ServiceRequestDto getById(UUID id);

    IdDto create(ServiceRequestDto dto);
}
