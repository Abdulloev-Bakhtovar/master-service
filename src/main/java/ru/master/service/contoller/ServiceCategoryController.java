package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.master.service.constant.DocumentType;
import ru.master.service.model.dto.request.CreateServiceCategoryDto;
import ru.master.service.model.dto.response.AllServiceCategoryDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubServiceDto;
import ru.master.service.service.FileStorageService;
import ru.master.service.service.ServiceCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-categories")
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;
    private final FileStorageService fileStorageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AllServiceCategoryDto> getAll(@RequestParam(required = false) String name) {
        return serviceCategoryService.getAll(name);
    }

    @GetMapping("/with-sub-services")
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceCategoryWithSubServiceDto> getAllWithSubServices() {
        return serviceCategoryService.getAllWithSubService();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceCategoryWithSubServiceDto getById(@PathVariable UUID id) {
        return serviceCategoryService.getById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ModelAttribute CreateServiceCategoryDto reqDto) throws Exception {
        serviceCategoryService.create(reqDto);
    }

    @GetMapping("/{id}/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {
        byte[] imageData = fileStorageService.loadFile(DocumentType.SERVICE_CATEGORY_PHOTO, id);
        MediaType mediaType = fileStorageService.getMediaType(DocumentType.SERVICE_CATEGORY_PHOTO, id);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageData);
    }
}
