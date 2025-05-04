package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.master.service.constant.DocumentType;
import ru.master.service.model.dto.NewsDto;
import ru.master.service.model.dto.request.CreateNewsReqDto;
import ru.master.service.service.FileStorageService;
import ru.master.service.service.NewsService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public List<NewsDto> getNews() {
        return newsService.getAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ModelAttribute CreateNewsReqDto dto) throws IOException {
        newsService.create(dto);
    }

    @PatchMapping("/{id}/hidden")
    public void hidden(@PathVariable UUID id) {
        newsService.changeVisibility(id, false);
    }

    @PatchMapping("/{id}/visible")
    public void visible(@PathVariable UUID id) {
        newsService.changeVisibility(id, true);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        newsService.delete(id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getNewsImage(@PathVariable UUID id) {
        byte[] imageData = fileStorageService.loadFile(DocumentType.NEWS_PHOTO, id);
        MediaType mediaType = fileStorageService.getMediaType(DocumentType.NEWS_PHOTO, id);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageData);
    }
}
