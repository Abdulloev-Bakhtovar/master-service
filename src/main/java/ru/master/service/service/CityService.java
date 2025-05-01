package ru.master.service.service;

import ru.master.service.model.dto.CityDto;
import ru.master.service.model.City;

import java.util.List;
import java.util.UUID;

public interface CityService {

    List<CityDto> getAll();

    City getById(UUID id);

    void create(CityDto reqDto);

    void changeVisibility(UUID id, boolean isVisible);
}
