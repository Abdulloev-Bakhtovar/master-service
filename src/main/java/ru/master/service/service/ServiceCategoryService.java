package ru.master.service.service;

import ru.master.service.model.dto.response.ServiceCategoryDto;
import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.request.ListServiceCategoryDto;

import java.util.List;
import java.util.UUID;

public interface ServiceCategoryService {

    List<ListServiceCategoryDto> getAll(String name);

    void create(CreateServiceCategoryDto dto) throws Exception;

    ServiceCategoryDto getById(UUID id);
}
