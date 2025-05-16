package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.master.service.constant.DocumentType;
import ru.master.service.model.dto.request.CreateServiceCategoryReqDto;
import ru.master.service.model.dto.response.ImageResDto;
import ru.master.service.model.dto.response.ServiceCategoryResDto;
import ru.master.service.model.dto.response.ServiceCategoryWithSubserviceResDto;
import ru.master.service.service.S3StorageService;
import ru.master.service.service.ServiceCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-categories")
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;
    private final S3StorageService fileStorageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceCategoryResDto> getAll(@RequestParam(required = false) String name) {
        return serviceCategoryService.getAll(name);
    }

    @GetMapping("/with-subservices")
    @ResponseStatus(HttpStatus.OK)
    public List<ServiceCategoryWithSubserviceResDto> getAllWithSubservices() {
        return serviceCategoryService.getAllWithSubservices();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ModelAttribute CreateServiceCategoryReqDto reqDto) throws Exception {
        serviceCategoryService.create(reqDto);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getServiceCategoryImage(@PathVariable UUID id) {
        ImageResDto image = fileStorageService.getImage(DocumentType.SERVICE_CATEGORY_PHOTO, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());
    }

}
