package ru.master.service.mapper;

import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.ServiceCategoryDto;

import java.util.Set;

public interface ServiceCategoryMapper {

    ServiceCategory toEntity(ServiceCategoryDto dto, Set<SubServiceCategory> subServices);

    ServiceCategoryDto toDto(ServiceCategory entity);
}
