package ru.master.service.service;

import ru.master.service.model.dto.request.CreateClientProfileReqDto;

public interface ClientProfileService {

    void create(CreateClientProfileReqDto reqDto);
}
