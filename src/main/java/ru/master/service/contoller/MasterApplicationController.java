package ru.master.service.contoller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.NewMasterRequestDto;
import ru.master.service.model.dto.MasterApplicationDto;
import ru.master.service.service.MasterApplicationService;

import java.util.List;

@PermitAll
@RestController
@RequestMapping("/master-applications")
@RequiredArgsConstructor
public class MasterApplicationController {

    private final MasterApplicationService masterApplicationService;

    @GetMapping
    public List<NewMasterRequestDto> getAll() {
        return masterApplicationService.getAll();
    }

    @PatchMapping("/approve")
    public void approve(@RequestBody MasterApplicationDto masterApplicationDto) {
        masterApplicationService.approve(masterApplicationDto);
    }

    @PatchMapping("/reject")
    public void reject(@RequestBody MasterApplicationDto masterApplicationDto) {
        masterApplicationService.reject(masterApplicationDto);
    }
}
