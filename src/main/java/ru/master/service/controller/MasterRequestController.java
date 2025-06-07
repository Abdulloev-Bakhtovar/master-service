package ru.master.service.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.RejectMasterRequestReqDto;
import ru.master.service.model.dto.response.MasterRequestResDto;
import ru.master.service.model.dto.response.NewMasterRequestResDto;
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
    public List<NewMasterRequestResDto> getAll() {
        return masterRequestService.getAll();
    }

    @GetMapping("/{requestId}")
    public MasterRequestResDto getById(@PathVariable UUID requestId) {
        return masterRequestService.getById(requestId);
    }

    @PatchMapping("/{requestId}/approve")
    public void approve(@PathVariable UUID requestId) {
        masterRequestService.approve(requestId);
    }

    @PatchMapping("/{requestId}/reject")
    public void reject(@PathVariable UUID requestId,
                       @RequestBody RejectMasterRequestReqDto reqDto) {
        masterRequestService.reject(requestId, reqDto);
    }
}
