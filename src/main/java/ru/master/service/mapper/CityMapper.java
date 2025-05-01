package ru.master.service.mapper;

import ru.master.service.model.dto.CityDto;
import ru.master.service.model.City;

public interface CityMapper {

    City toEntity(CityDto dto);

    CityDto toDto(City entity);
}
