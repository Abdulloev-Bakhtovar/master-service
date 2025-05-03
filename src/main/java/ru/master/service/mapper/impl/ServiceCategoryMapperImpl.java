package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.dto.SubserviceForServiceDto;
import ru.master.service.model.dto.request.CreateServiceCategoryReqDto;
import ru.master.service.model.dto.response.ServiceCategoryResDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubserviceResDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceCategoryMapperImpl implements ServiceCategoryMapper {

    @Override
    public ServiceCategoryResDto toServiceCategoryResDto(ServiceCategory serviceCategory) {
        if (serviceCategory == null) return null;

        return ServiceCategoryResDto.builder()
                .id(serviceCategory.getId())
                .name(serviceCategory.getName())
                .createdAt(serviceCategory.getCreatedAt())
                .updatedAt(serviceCategory.getUpdatedAt())
                .build();
    }

    @Override
    public ServiceCategory toEntity(CreateServiceCategoryReqDto entity) {
        if (entity == null) return null;

        return ServiceCategory.builder()
                .name(entity.getName())
                .build();
    }

    @Override
    public ServiceCategoryWithSubserviceResDto ServiceCategoryWithSubserviceResDto(ServiceCategory serviceCategory) {
        if (serviceCategory == null) return null;

        List<SubserviceForServiceDto> subservice = serviceCategory.getSubservices().stream()
                .map(sub -> SubserviceForServiceDto.builder()
                        .id(sub.getId())
                        .name(sub.getName())
                        .createdAt(sub.getCreatedAt())
                        .updatedAt(sub.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return ServiceCategoryWithSubserviceResDto.builder()
                .id(serviceCategory.getId())
                .name(serviceCategory.getName())
                .createdAt(serviceCategory.getCreatedAt())
                .updatedAt(serviceCategory.getUpdatedAt())
                .subserviceDtos(subservice)
                .build();
    }
}
