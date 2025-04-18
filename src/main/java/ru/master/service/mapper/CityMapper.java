package ru.master.service.mapper;

import ru.master.service.model.City;
import ru.master.service.model.dto.CityDto;

public interface CityMapper {

    City toEntity(CityDto dto);

    CityDto toDto(City entity);
}
