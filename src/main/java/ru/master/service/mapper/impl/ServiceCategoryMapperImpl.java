package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.mapper.SubServiceCategoryMapper;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.ServiceCategoryDto;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ServiceCategoryMapperImpl implements ServiceCategoryMapper {

    private final SubServiceCategoryMapper subServiceMapper;

    @Override
    public ServiceCategory toEntity(ServiceCategoryDto dto, Set<SubServiceCategory> subServices) {
        if (dto == null) return null;

        return ServiceCategory.builder()
                .name(dto.getName())
                .subServices(subServices)
                .build();
    }

    @Override
    public ServiceCategoryDto toDto(ServiceCategory entity) {
        if (entity == null) return null;

        return ServiceCategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .subServiceCategoryDtos(subServiceMapper.toDtoList(entity.getSubServices()))
                .build();
    }

    private ServiceCategoryDto toDto(MasterSubService entity) {
        if (entity == null) return null;

        return ServiceCategoryDto.builder()
                .id(entity.getServiceCategory().getId())
                .name(entity.getServiceCategory().getName())
                .subServiceCategoryDtos(subServiceMapper.toDtoList(entity.getServiceCategory().getSubServices()))
                .build();
    }

    @Override
    public List<ServiceCategoryDto> toDtoList(List<MasterSubService> masterSubServices) {

        return masterSubServices.stream()
                .map(this::toDto)
                .toList();
    }
}
