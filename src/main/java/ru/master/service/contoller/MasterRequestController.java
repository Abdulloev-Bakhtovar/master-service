package ru.master.service.contoller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.NewMasterRequestDto;
import ru.master.service.model.dto.MasterRequestDto;
import ru.master.service.service.MasterRequestService;

import java.util.List;
import java.util.UUID;

@PermitAll
@RestController
@RequestMapping("/master-applications")
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

    @PatchMapping("/approve")
    public void approve(@RequestBody MasterRequestDto masterRequestDto) {
        masterRequestService.approve(masterRequestDto);
    }

    @PatchMapping("/reject")
    public void reject(@RequestBody MasterRequestDto masterRequestDto) {
        masterRequestService.reject(masterRequestDto);
    }
}
