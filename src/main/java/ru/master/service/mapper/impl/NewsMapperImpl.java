package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.NewsMapper;
import ru.master.service.model.City;
import ru.master.service.model.News;
import ru.master.service.model.dto.NewsDto;

@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public News toEntity(NewsDto dto, City city) {
        if (dto == null) return null;

        return News.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .city(city)
                .isVisible(dto.isVisible())
                .build();
    }
}
