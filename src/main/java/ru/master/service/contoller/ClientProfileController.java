package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.master.service.model.dto.ClientProfileDto;
import ru.master.service.service.ClientProfileService;

@RestController
@RequestMapping("/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;

    @PostMapping
    public void create(@RequestBody ClientProfileDto clientProfileDto) {
        clientProfileService.create(clientProfileDto);
    }
}
