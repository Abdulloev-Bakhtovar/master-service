package ru.master.service.service;


import ru.master.service.model.dto.request.CreateSubServiceCategoryDto;
import ru.master.service.model.dto.response.AllSubServiceCategoryDto;

import java.util.List;

public interface SubServiceCategoryService {

    List<AllSubServiceCategoryDto> getAll();

    void create(CreateSubServiceCategoryDto reqDto);
}
