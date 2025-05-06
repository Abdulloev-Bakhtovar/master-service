package ru.master.service.service;

import ru.master.service.model.dto.request.CreateClientProfileReqDto;
import ru.master.service.model.dto.response.ClientInfoForCreateOrderResDto;

public interface ClientProfileService {

    void create(CreateClientProfileReqDto reqDto);

    ClientInfoForCreateOrderResDto getClientInfo();
}
