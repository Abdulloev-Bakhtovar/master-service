package ru.master.service.service;

import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ServiceRequestDto;

public interface ServiceRequestService {

    IdDto create(ServiceRequestDto dto);
}
