package ru.master.service.service;

import ru.master.service.model.dto.SubServiceCategoryDto;

import java.util.List;

public interface SubServiceCategoryService {

    List<SubServiceCategoryDto> getAll();

    void create(SubServiceCategoryDto dto);
}
