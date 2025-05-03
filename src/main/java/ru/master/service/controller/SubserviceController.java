package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CreateSubserviceReqDto;
import ru.master.service.model.dto.response.SubserviceResDto;
import ru.master.service.service.SubserviceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service-categories")
@RequiredArgsConstructor
public class SubserviceController {

    private final SubserviceService subserviceService;

    @GetMapping("/{serviceId}/subservices")
    @ResponseStatus(HttpStatus.OK)
    public List<SubserviceResDto> getAllByServiceId(@PathVariable UUID serviceId) {
        return subserviceService.getAllByServiceId(serviceId);
    }

    @PostMapping("/{serviceId}/add-subservice")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable UUID serviceId, @RequestBody CreateSubserviceReqDto reqDto) {
        subserviceService.create(serviceId, reqDto);
    }
}
