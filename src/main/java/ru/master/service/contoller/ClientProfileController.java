package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.ClientProfileDto;
import ru.master.service.service.ClientProfileService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client-profile")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;

    @GetMapping
    public List<ClientProfileDto> getAll() {
        return new ArrayList<>();
    }

    @PostMapping
    public void create(@RequestBody ClientProfileDto clientProfileDto) {
        clientProfileService.create(clientProfileDto);
    }
}
