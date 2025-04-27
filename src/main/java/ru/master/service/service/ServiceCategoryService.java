package ru.master.service.service;

import ru.master.service.model.dto.ServiceCategoryDto;
import ru.master.service.model.dto.request.ServiceCategoryReqDto;

import java.util.List;
import java.util.UUID;

public interface ServiceCategoryService {

    List<ServiceCategoryDto> getAll(String name);

    void create(ServiceCategoryReqDto dto) throws Exception;

    List<ServiceCategoryDto> getAllWithSubService();

    ServiceCategoryDto getById(UUID id);
}
