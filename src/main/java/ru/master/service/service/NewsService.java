package ru.master.service.service;

import ru.master.service.model.dto.response.NewsDto;
import ru.master.service.model.dto.request.CreateNewsDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface NewsService {

    List<NewsDto> getAll();

    void create(CreateNewsDto dto) throws IOException;

    void changeVisibility(UUID id, boolean isVisible);

    void delete(UUID id);
}
