package ru.master.service.service;

import ru.master.service.model.dto.NewsDto;

import java.io.IOException;

public interface NewsService {

    void create(NewsDto dto) throws IOException;
}
