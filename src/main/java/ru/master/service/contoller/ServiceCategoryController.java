package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.master.service.constants.DocumentType;
import ru.master.service.model.dto.ServiceCategoryDto;
import ru.master.service.model.dto.request.ServiceCategoryReqDto;
import ru.master.service.service.FileStorageService;
import ru.master.service.service.ServiceCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-categories")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public List<ServiceCategoryDto> getAll(@RequestParam(required = false) String name) {
        return serviceCategoryService.getAll(name);
    }

    @GetMapping("/with-sub-service")
    public List<ServiceCategoryDto> getAllWithSubService() {
        return serviceCategoryService.getAllWithSubService();
    }

    @GetMapping("/{id}")
    public ServiceCategoryDto getById(@PathVariable UUID id) {
        return serviceCategoryService.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ModelAttribute ServiceCategoryReqDto dto) throws Exception {
        serviceCategoryService.create(dto);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {
        byte[] imageData = fileStorageService.loadFile(DocumentType.SERVICE_CATEGORY_PHOTO, id);
        MediaType mediaType = fileStorageService.getMediaType(DocumentType.SERVICE_CATEGORY_PHOTO, id);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageData);
    }
}
