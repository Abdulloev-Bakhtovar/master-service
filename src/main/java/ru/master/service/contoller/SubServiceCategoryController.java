package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.SubServiceCategoryDto;
import ru.master.service.service.SubServiceCategoryService;

import java.util.List;

@RestController
@RequestMapping("/subservice-categories")
@RequiredArgsConstructor
public class SubServiceCategoryController {

    private final SubServiceCategoryService subServiceCategoryService;

    @GetMapping
    public List<SubServiceCategoryDto> getAll() {
        return subServiceCategoryService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SubServiceCategoryDto dto) {
        subServiceCategoryService.create(dto);
    }
}
