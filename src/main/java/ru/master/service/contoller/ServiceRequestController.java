package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ServiceRequestDto;
import ru.master.service.service.ServiceRequestService;

@RestController
@RequestMapping("/service-requests")
@RequiredArgsConstructor
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    @PostMapping
    public IdDto create(@RequestBody ServiceRequestDto dto) {
        return serviceRequestService.create(dto);
    }
}
