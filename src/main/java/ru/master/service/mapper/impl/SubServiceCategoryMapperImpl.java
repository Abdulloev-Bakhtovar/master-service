package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.SubServiceCategoryMapper;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.SubServiceCategoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SubServiceCategoryMapperImpl implements SubServiceCategoryMapper {

    @Override
    public SubServiceCategory toEntity(SubServiceCategoryDto dto) {
        if (dto == null) return null;

        return SubServiceCategory.builder()
                .name(dto.getName())
                .build();
    }

    @Override
    public SubServiceCategoryDto toDto(SubServiceCategory entity) {
        if (entity == null) return null;

        return SubServiceCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public List<SubServiceCategory> toEntityList(Set<SubServiceCategoryDto> dtos) {
        if (dtos == null) return new ArrayList<>();

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubServiceCategoryDto> toDtoList(List<SubServiceCategory> entities) {
        if (entities == null) return new ArrayList<>();

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
