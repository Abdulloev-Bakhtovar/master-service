package ru.master.service.mapper;

import ru.master.service.model.ServiceCategory;
import ru.master.service.model.Subservice;
import ru.master.service.model.dto.request.CreateSubserviceReqDto;
import ru.master.service.model.dto.response.SubserviceResDto;

public interface SubserviceMapper {

    Subservice toEntity(CreateSubserviceReqDto reqDto, ServiceCategory serviceCategory);

    SubserviceResDto toSubserviceResDto(Subservice subservice);
}
