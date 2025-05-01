package ru.master.service.contoller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.MasterRequestRejectDto;
import ru.master.service.model.dto.response.MasterRequestDto;
import ru.master.service.model.dto.response.NewMasterRequestDto;
import ru.master.service.service.MasterRequestService;

import java.util.List;
import java.util.UUID;

@PermitAll
@RestController
@RequestMapping("/master-requests")
@RequiredArgsConstructor
public class MasterRequestController {

    private final MasterRequestService masterRequestService;

    @GetMapping
    public List<NewMasterRequestDto> getAll() {
        return masterRequestService.getAll();
    }

    @GetMapping("/{id}")
    public MasterRequestDto getById(@PathVariable UUID id) {
        return masterRequestService.getById(id);
    }

    @PatchMapping("/approve/{id}")
    public void approve(@PathVariable UUID id) {
        masterRequestService.approve(id);
    }

    @PatchMapping("/reject")
    public void reject(@RequestBody MasterRequestRejectDto rejectDto) {
        masterRequestService.reject(rejectDto);
    }

}
