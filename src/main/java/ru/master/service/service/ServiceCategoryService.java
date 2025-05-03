package ru.master.service.service;

import ru.master.service.model.dto.request.CreateServiceCategoryReqDto;
import ru.master.service.model.dto.response.ServiceCategoryResDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubserviceResDto;

import java.util.List;

public interface ServiceCategoryService {

    List<ServiceCategoryResDto> getAll(String name);

    void create(CreateServiceCategoryReqDto reqDto) throws Exception;

    List<ServiceCategoryWithSubserviceResDto> getAllWithSubservices();
}
