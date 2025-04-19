package ru.master.service.service;

import ru.master.service.model.dto.ServiceCategoryDto;

import java.util.List;

public interface ServiceCategoryService {

    List<ServiceCategoryDto> getAll();

    void create(ServiceCategoryDto dto);
}
