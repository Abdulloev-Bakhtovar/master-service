package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.SubServiceCategoryMapper;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.SubServiceForServiceDto;
import ru.master.service.model.dto.request.CreateSubServiceCategoryDto;
import ru.master.service.model.dto.response.AllSubServiceCategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubServiceCategoryMapperImpl implements SubServiceCategoryMapper {

    @Override
    public SubServiceCategory toEntity(CreateSubServiceCategoryDto dto, ServiceCategory serviceCategory) {
        if (dto == null) return null;

        return SubServiceCategory.builder()
                .name(dto.getName())
                .serviceCategory(serviceCategory)
                .build();
    }

    @Override
    public AllSubServiceCategoryDto toDto(SubServiceCategory entity) {
        if (entity == null) return null;

        return AllSubServiceCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<SubServiceForServiceDto> toDtoList(List<SubServiceCategory> entities) {
        if (entities == null) return new ArrayList<>();

        return entities.stream()
                .map(this::toSubServiceForServiceDto)
                .toList();
    }

    @Override
    public SubServiceForServiceDto toOrderInfoForClientDto(SubServiceCategory entity) {
        if (entity == null) return null;

        return SubServiceForServiceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private SubServiceForServiceDto toSubServiceForServiceDto(SubServiceCategory entity) {
        if (entity == null) return null;

        return SubServiceForServiceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
