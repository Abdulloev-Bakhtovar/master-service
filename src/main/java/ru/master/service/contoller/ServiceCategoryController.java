package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.ServiceCategoryDto;
import ru.master.service.service.ServiceCategoryService;

import java.util.List;

@RestController
@RequestMapping("/service-category")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    @GetMapping
    public List<ServiceCategoryDto> getAll() {
        return serviceCategoryService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ServiceCategoryDto dto) {
        serviceCategoryService.create(dto);
    }
}
