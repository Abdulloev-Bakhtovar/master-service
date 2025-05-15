package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.DocumentType;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.NewsMapper;
import ru.master.service.model.dto.NewsDto;
import ru.master.service.model.dto.request.CreateNewsReqDto;
import ru.master.service.repository.CityRepo;
import ru.master.service.repository.NewsRepo;
import ru.master.service.service.NewsService;
import ru.master.service.service.S3StorageService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final CityRepo cityRepo;
    private final NewsRepo newsRepo;
    private final NewsMapper newsMapper;
    private final S3StorageService fileStorageService;

    @Override
    public List<NewsDto> getAll() {
        return newsRepo.findByIsVisibleTrue().stream()
                .map(newsMapper::toDto)
                .toList();
    }

    @Override
    public void create(CreateNewsReqDto dto) throws IOException {

        var city = cityRepo.findById(dto.getCityId())
                .orElseThrow(() -> new AppException(
                        ErrorMessage.CITY_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var news = newsMapper.toEntity(dto, city);
        newsRepo.save(news);

        fileStorageService.storeFile(dto.getPhoto(), DocumentType.NEWS_PHOTO, news.getId());
    }

    @Override
    public void changeVisibility(UUID id, boolean isVisible) {
        var news = newsRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.NEWS_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        news.setVisible(isVisible);
        newsRepo.save(news);
    }

    @Override
    public void delete(UUID id) {
        newsRepo.deleteById(id);
    }
}
