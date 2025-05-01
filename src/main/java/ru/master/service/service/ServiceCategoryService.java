package ru.master.service.service;

import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.response.AllServiceCategoryDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubServiceDto;

import java.util.List;
import java.util.UUID;

public interface ServiceCategoryService {

    List<AllServiceCategoryDto> getAll(String name);

    List<ServiceCategoryWithSubServiceDto> getAllWithSubService();

    void create(CreateServiceCategoryDto reqDto) throws Exception;

    ServiceCategoryWithSubServiceDto getById(UUID id);
}
