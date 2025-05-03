package ru.master.service.service;

import ru.master.service.model.dto.request.CreateSubserviceReqDto;
import ru.master.service.model.dto.response.SubserviceResDto;

import java.util.List;
import java.util.UUID;

public interface SubserviceService {

    List<SubserviceResDto> getAllByServiceId(UUID serviceId);

    void create(UUID serviceId, CreateSubserviceReqDto reqDto);
}
