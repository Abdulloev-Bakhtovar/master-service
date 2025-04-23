package ru.master.service.mapper;

import ru.master.service.model.City;
import ru.master.service.model.News;
import ru.master.service.model.dto.NewsDto;

public interface NewsMapper {

    News toEntity(NewsDto dto, City city);

    NewsDto toDto(News entity);
}
