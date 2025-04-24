package ru.master.service.mapper;

import ru.master.service.model.MasterSubService;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.ServiceCategoryDto;
import ru.master.service.model.dto.request.ServiceCategoryReqDto;

import java.util.List;

public interface ServiceCategoryMapper {

    ServiceCategory toEntity(ServiceCategoryReqDto dto, List<SubServiceCategory> subServices);

    ServiceCategoryDto toDto(ServiceCategory entity);

    List<ServiceCategoryDto> toDtoList(List<MasterSubService> masterSubServices);
}
