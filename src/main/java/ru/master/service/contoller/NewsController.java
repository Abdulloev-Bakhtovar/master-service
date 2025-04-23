package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.model.News;
import ru.master.service.model.dto.CityDto;
import ru.master.service.model.dto.NewsDto;
import ru.master.service.service.NewsService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public List<News> getNews() {
        return List.of();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void create(
            @RequestParam String title,
            @RequestParam UUID cityId,
            @RequestParam String content,
            @RequestParam boolean isVisible,
            @RequestParam MultipartFile photo
    ) throws IOException {

        var cityDto = CityDto.builder()
                .id(cityId)
                .build();

        var newsDto = NewsDto.builder()
                .title(title)
                .content(content)
                .isVisible(isVisible)
                .cityDto(cityDto)
                .photo(photo)
                .build();

        newsService.create(newsDto);
    }
}
