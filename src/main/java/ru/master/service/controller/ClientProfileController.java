package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CancelOrderForClientDto;
import ru.master.service.model.dto.request.CompleteOrderForClientDto;
import ru.master.service.model.dto.request.CreateClientProfileReqDto;
import ru.master.service.model.dto.response.AllClientOrderResDto;
import ru.master.service.model.dto.response.OrderDetailForClientResDto;
import ru.master.service.service.ClientProfileService;
import ru.master.service.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateClientProfileReqDto reqDto) {
        clientProfileService.create(reqDto);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<AllClientOrderResDto> getAllClientOrders() {
        return orderService.getAllClientOrders();
    }


    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailForClientResDto getByIdForClient(@PathVariable UUID orderId) {
        return orderService.getByIdForClient(orderId);
    }

    @PatchMapping("/orders/{orderId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrderForClient(@PathVariable UUID orderId, @RequestBody CancelOrderForClientDto reqDto) {
        orderService.cancelOrderForClient(orderId, reqDto);
    }

    @PatchMapping("/orders/{orderId}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeOrderForClient(@PathVariable UUID orderId, @RequestBody CompleteOrderForClientDto reqDto) {
        orderService.completeOrderForClient(orderId, reqDto);
    }
}
