package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.CityMapper;
import ru.master.service.model.City;
import ru.master.service.model.dto.CityDto;

@Component
public class CityMapperImpl implements CityMapper {

    @Override
    public City toEntity(CityDto dto) {
        if (dto == null) return null;

        return City.builder()
                .name(dto.getName())
                .isVisible(dto.isVisible())
                .build();
    }

    @Override
    public CityDto toDto(City entity) {
        if (entity == null) return null;

        return CityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isVisible(entity.isVisible())
                .build();
    }
}
