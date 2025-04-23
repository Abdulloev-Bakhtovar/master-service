package ru.master.service.service;

import ru.master.service.model.dto.NewsDto;

import java.io.IOException;
import java.util.List;

public interface NewsService {

    List<NewsDto> getAll();

    void create(NewsDto dto) throws IOException;
}
