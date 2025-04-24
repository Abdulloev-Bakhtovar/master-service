package ru.master.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.master.service.mapper.CityMapper;
import ru.master.service.mapper.NewsMapper;
import ru.master.service.model.City;
import ru.master.service.model.News;
import ru.master.service.model.dto.NewsDto;
import ru.master.service.model.dto.request.NewsCreateReqDto;

@Component
@RequiredArgsConstructor
public class NewsMapperImpl implements NewsMapper {

    private final CityMapper cityMapper;

    @Override
    public News toEntity(NewsCreateReqDto dto, City city) {
        if (dto == null) return null;

        return News.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .city(city)
                .isVisible(dto.isVisible())
                .build();
    }

    @Override
    public NewsDto toDto(News entity) {
        if (entity == null) return null;

        return NewsDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .title(entity.getTitle())
                .content(entity.getContent())
                .cityDto(cityMapper.toDto(entity.getCity()))
                .isVisible(entity.isVisible())
                .build();
    }
}
