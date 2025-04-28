package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ServiceRequestDto;
import ru.master.service.model.dto.request.ServiceRequestInfoDto;
import ru.master.service.service.ServiceRequestService;

import java.util.UUID;

@RestController
@RequestMapping("/service-requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @GetMapping("/{id}")
    public ServiceRequestInfoDto findById(@PathVariable UUID id) {
        return serviceRequestService.getById(id);
    }

    @PostMapping
    public IdDto create(@RequestBody ServiceRequestDto dto) {
        return serviceRequestService.create(dto);
    }
}
