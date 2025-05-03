package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.SubserviceMapper;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.Subservice;
import ru.master.service.model.dto.SubserviceForServiceDto;
import ru.master.service.model.dto.request.CreateSubserviceReqDto;
import ru.master.service.model.dto.response.SubserviceResDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubserviceMapperImpl implements SubserviceMapper {

    @Override
    public Subservice toEntity(CreateSubserviceReqDto dto, ServiceCategory serviceCategory) {
        if (dto == null) return null;

        return Subservice.builder()
                .name(dto.getName())
                .serviceCategory(serviceCategory)
                .build();
    }

    @Override
    public SubserviceResDto toSubserviceResDto(Subservice entity) {
        if (entity == null) return null;

        return SubserviceResDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
