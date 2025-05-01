package ru.master.service.mapper;

import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;
import ru.master.service.model.dto.SubServiceForServiceDto;
import ru.master.service.model.dto.request.CreateSubServiceCategoryDto;
import ru.master.service.model.dto.response.AllSubServiceCategoryDto;

import java.util.List;

public interface SubServiceCategoryMapper {

    SubServiceCategory toEntity(CreateSubServiceCategoryDto reqDto, ServiceCategory serviceCategory);

    AllSubServiceCategoryDto toDto(SubServiceCategory subServiceCategory);

    List<SubServiceForServiceDto> toDtoList(List<SubServiceCategory> entities);

    SubServiceForServiceDto toOrderInfoForClientDto(SubServiceCategory subServiceCategory);
}
