package ru.master.service.mapper;

import ru.master.service.model.ClientOrder;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.inner.ServiceCategoryForOrderDto;
import ru.master.service.model.dto.responce.ServiceCategoryDto;
import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.request.ListServiceCategoryDto;

import java.util.List;

public interface ServiceCategoryMapper {

    ServiceCategory toEntity(CreateServiceCategoryDto dto, List<SubServiceCategory> subServices);

    ServiceCategoryDto toDto(ServiceCategory entity);

    List<ServiceCategoryDto> toDtoList(List<MasterSubService> masterSubServices);

    ServiceCategoryDto toDtoWithSubService(ServiceCategory entity);

    ListServiceCategoryDto toListServiceCategoryDto(ServiceCategory serviceCategory);

    ServiceCategoryForOrderDto toServiceCategoryForOrderDto(ClientOrder clientOrder);
}
