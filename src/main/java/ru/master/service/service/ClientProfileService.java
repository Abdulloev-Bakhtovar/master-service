package ru.master.service.service;

import ru.master.service.model.dto.request.CreateClientProfileDto;

public interface ClientProfileService {

    void create(CreateClientProfileDto reqDto);
}
