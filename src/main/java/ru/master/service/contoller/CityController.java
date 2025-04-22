package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.CityDto;
import ru.master.service.service.CityService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public List<CityDto> getAll() {
        return cityService.getAll();
    }

    @PostMapping
    public void create(@RequestBody CityDto cityDto) {
        cityService.create(cityDto);
    }

    @PatchMapping("/{id}/hidden")
    public void hidden(@PathVariable UUID id) {
        cityService.changeVisibility(id, false);
    }

    @PatchMapping("/{id}/visible")
    public void visible(@PathVariable UUID id) {
        cityService.changeVisibility(id, true);
    }
}
