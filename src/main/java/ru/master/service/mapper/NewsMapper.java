package ru.master.service.mapper;

import ru.master.service.model.City;
import ru.master.service.model.News;
import ru.master.service.model.dto.NewsDto;
import ru.master.service.model.dto.request.NewsCreateReqDto;

public interface NewsMapper {

    News toEntity(NewsCreateReqDto dto, City city);

    NewsDto toDto(News entity);
}
