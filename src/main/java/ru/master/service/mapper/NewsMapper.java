package ru.master.service.mapper;

import ru.master.service.model.City;
import ru.master.service.model.News;
import ru.master.service.model.dto.request.CreateNewsDto;
import ru.master.service.model.dto.response.NewsDto;

public interface NewsMapper {

    News toEntity(CreateNewsDto dto, City city);

    NewsDto toDto(News entity);
}
