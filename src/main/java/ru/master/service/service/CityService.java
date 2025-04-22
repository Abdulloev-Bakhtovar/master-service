package ru.master.service.service;

import ru.master.service.model.City;
import ru.master.service.model.dto.CityDto;

import java.util.List;
import java.util.UUID;

public interface CityService {

    List<CityDto> getAll();

    City getById(UUID id);

    void create(CityDto dto);

    void changeVisibility(UUID id, boolean isVisible);
}
