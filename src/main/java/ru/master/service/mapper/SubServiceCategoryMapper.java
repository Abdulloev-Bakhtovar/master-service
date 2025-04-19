package ru.master.service.mapper;

import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.SubServiceCategoryDto;

import java.util.Set;

public interface SubServiceCategoryMapper {

    SubServiceCategory toEntity(SubServiceCategoryDto dto);

    SubServiceCategoryDto toDto(SubServiceCategory entity);

    Set<SubServiceCategory> toEntityList(Set<SubServiceCategoryDto> dtos);

    Set<SubServiceCategoryDto> toDtoList(Set<SubServiceCategory> entities);
}
