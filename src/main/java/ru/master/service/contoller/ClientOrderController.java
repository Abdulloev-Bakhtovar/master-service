package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.IdDto;
import ru.master.service.model.dto.ClientOrderDto;
import ru.master.service.model.dto.request.ServiceRequestInfoDto;
import ru.master.service.service.ClientOrderService;

import java.util.UUID;

@RestController
@RequestMapping("/client-orders")
@RequiredArgsConstructor
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    @GetMapping("/{id}")
    public ServiceRequestInfoDto findById(@PathVariable UUID id) {
        return clientOrderService.getById(id);
    }

    @PostMapping
    public IdDto create(@RequestBody ClientOrderDto dto) {
        return clientOrderService.create(dto);
    }

    @PatchMapping("/orders/{orderId}/accept")
    public void acceptOrder(@PathVariable UUID orderId) {
        clientOrderService.acceptOrder(orderId);
    }
}
