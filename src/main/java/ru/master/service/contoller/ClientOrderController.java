package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.IdDto;
import ru.master.service.service.ClientOrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@RequestBody CreateClientOrderDto reqDto) {
        return clientOrderService.create(reqDto);
    }
}
