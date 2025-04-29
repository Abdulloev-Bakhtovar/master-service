package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CreateClientProfileDto;
import ru.master.service.service.ClientProfileService;

@RestController
@RequestMapping("/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateClientProfileDto reqDto) {
        clientProfileService.create(reqDto);
    }
}
