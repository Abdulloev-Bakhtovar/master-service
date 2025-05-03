package ru.master.service.service;

import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.response.ServiceCategoryResDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubServiceDto;

import java.util.List;
import java.util.UUID;

public interface ServiceCategoryService {

    List<ServiceCategoryResDto> getAll(String name);

    List<ServiceCategoryWithSubServiceDto> getAllWithSubService();

    void create(CreateServiceCategoryDto reqDto) throws Exception;

    ServiceCategoryWithSubServiceDto getById(UUID id);
}
