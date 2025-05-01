package ru.master.service.mapper;


import ru.master.service.model.ClientOrder;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.dto.ServiceCategoryForClientOrderDto;
import ru.master.service.model.dto.ServiceCategoryForMasterProfileDto;
import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.response.AllServiceCategoryDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubServiceDto;

import java.util.List;

public interface ServiceCategoryMapper {

    AllServiceCategoryDto toAllServiceCategoryDto(ServiceCategory serviceCategory);

    ServiceCategoryWithSubServiceDto toDtoWithSubService(ServiceCategory serviceCategory);

    ServiceCategory toEntity(CreateServiceCategoryDto reqDto);

    ServiceCategoryForClientOrderDto toOrderInfoForClientDto(ClientOrder serviceCategory);

    List<ServiceCategoryForMasterProfileDto> toDtoList(List<MasterSubService> masterSubServices);
}
