package ru.master.service.service;

import ru.master.service.model.dto.ServiceCategoryDto;
import ru.master.service.model.dto.request.ServiceCategoryReqDto;

import java.util.List;

public interface ServiceCategoryService {

    List<ServiceCategoryDto> getAll(String name);

    void create(ServiceCategoryReqDto dto) throws Exception;

    List<ServiceCategoryDto> getAllWithSubService();
}
