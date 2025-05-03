package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.mapper.ServiceCategoryMapper;
import ru.master.service.mapper.SubServiceCategoryMapper;
import ru.master.service.model.ClientOrder;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.dto.ServiceCategoryForClientOrderDto;
import ru.master.service.model.dto.ServiceCategoryForMasterProfileDto;
import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.response.ServiceCategoryResDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubServiceDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceCategoryMapperImpl implements ServiceCategoryMapper {

    private final SubServiceCategoryMapper subServiceMapper;

    @Override
    public ServiceCategoryResDto toAllServiceCategoryDto(ServiceCategory entity) {
        if (entity == null) return null;

        return ServiceCategoryResDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public ServiceCategoryWithSubServiceDto toDtoWithSubService(ServiceCategory entity) {
        if (entity == null) return null;

        return ServiceCategoryWithSubServiceDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .subServiceCategoryDtos(subServiceMapper.toDtoList(entity.getSubServices()))
                .build();
    }

    @Override
    public ServiceCategory toEntity(CreateServiceCategoryDto entity) {
        if (entity == null) return null;

        return ServiceCategory.builder()
                .name(entity.getName())
                .build();
    }

    @Override
    public ServiceCategoryForClientOrderDto toOrderInfoForClientDto(ClientOrder entity) {
        if (entity == null) return null;

        return ServiceCategoryForClientOrderDto.builder()
                .id(entity.getServiceCategory().getId())
                .name(entity.getServiceCategory().getName())
                .createdAt(entity.getServiceCategory().getCreatedAt())
                .updatedAt(entity.getServiceCategory().getUpdatedAt())
                .subServiceCategoryDto(subServiceMapper.toOrderInfoForClientDto(entity.getSubServiceCategory()))
                .build();
    }

    @Override
    public List<ServiceCategoryForMasterProfileDto> toDtoList(List<MasterSubService> masterSubServices) {
        if (masterSubServices == null) return new ArrayList<>(); // TODO

        return masterSubServices.stream()
                .map(this::toDtoWithSubService)
                .toList();
    }

    private ServiceCategoryForMasterProfileDto toDtoWithSubService(MasterSubService entity) {
        if (entity == null) return null;

        return ServiceCategoryForMasterProfileDto.builder()
                .id(entity.getServiceCategory().getId())
                .name(entity.getServiceCategory().getName())
                .createdAt(entity.getServiceCategory().getCreatedAt())
                .updatedAt(entity.getServiceCategory().getUpdatedAt())
                .subServiceCategoryDtos(subServiceMapper.toDtoList(entity.getServiceCategory().getSubServices()))
                .build();
    }
}
