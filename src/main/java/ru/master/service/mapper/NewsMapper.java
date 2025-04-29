package ru.master.service.mapper;

import ru.master.service.model.City;
import ru.master.service.model.News;
import ru.master.service.model.dto.NewsDto;
import ru.master.service.model.dto.request.CreateNewsDto;

public interface NewsMapper {

    News toEntity(CreateNewsDto dto, City city);

    NewsDto toDto(News entity);
}
