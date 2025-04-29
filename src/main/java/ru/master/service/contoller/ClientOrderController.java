package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.response.IdDto;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientOrderDto;
import ru.master.service.model.dto.response.ListClientOrderDto;
import ru.master.service.model.dto.response.OrderInfoDto;
import ru.master.service.service.ClientOrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    @GetMapping("/client-orders")
    public List<ListClientOrderDto> getClientOrders() {
        return clientOrderService.getClientOrders();
    }

    @PatchMapping("/cancel")
    public void cancelOrderForClient(@RequestBody CancelOrderDto reqDto) {
        clientOrderService.cancelOrderForClient(reqDto);
    }

    @PatchMapping("/complete")
    public void completeOrderForClient(@RequestBody CompleteOrderDto reqDto) {
        clientOrderService.completeOrderForClient(reqDto);
    }

    @GetMapping("/{id}")
    public OrderInfoDto getById(@PathVariable UUID id) {
        return clientOrderService.getById(id);
    }

    @PostMapping
    public IdDto create(@RequestBody CreateClientOrderDto reqDto) {
        return clientOrderService.create(reqDto);
    }

    @PatchMapping("/{orderId}/accept")
    public void acceptOrder(@PathVariable UUID orderId) {
        clientOrderService.acceptOrder(orderId);
    }
}
