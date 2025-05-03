package ru.master.service.mapper;

import ru.master.service.model.ServiceCategory;
import ru.master.service.model.dto.request.CreateServiceCategoryReqDto;
import ru.master.service.model.dto.response.ServiceCategoryResDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubserviceResDto;

public interface ServiceCategoryMapper {

    /**
     * For get all service categories
     */
    ServiceCategoryResDto toServiceCategoryResDto(ServiceCategory serviceCategory);

    ServiceCategory toEntity(CreateServiceCategoryReqDto reqDto);

    /**
     * For get all service categories with subservices
     */
    ServiceCategoryWithSubserviceResDto ServiceCategoryWithSubserviceResDto(ServiceCategory serviceCategory);
}
