package ru.master.service.mapper;

import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.SubServiceCategoryDto;

import java.util.List;
import java.util.Set;

public interface SubServiceCategoryMapper {

    SubServiceCategory toEntity(SubServiceCategoryDto dto);

    SubServiceCategoryDto toDto(SubServiceCategory entity);

    List<SubServiceCategory> toEntityList(Set<SubServiceCategoryDto> dtos);

    List<SubServiceCategoryDto> toDtoList(List<SubServiceCategory> entities);
}
