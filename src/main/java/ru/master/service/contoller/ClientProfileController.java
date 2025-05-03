package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CancelOrderDto;
import ru.master.service.model.dto.request.CompleteOrderDto;
import ru.master.service.model.dto.request.CreateClientProfileReqDto;
import ru.master.service.model.dto.response.AllClientProfileOrderDto;
import ru.master.service.model.dto.response.OrderInfoForClientDto;
import ru.master.service.service.ClientOrderService;
import ru.master.service.service.ClientProfileService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;
    private final ClientOrderService clientOrderService;

    @PostMapping("/profiles")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateClientProfileReqDto reqDto) {
        clientProfileService.create(reqDto);
    }

    @GetMapping("/my-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<AllClientProfileOrderDto> getMyOrdersForClientProfile() {
        return clientOrderService.getMyOrdersForClientProfile();
    }

    @GetMapping("/order-info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderInfoForClientDto getById(@PathVariable UUID id) {
        return clientOrderService.getOrderInfoForClient(id);
    }

    @PatchMapping("/cancel-order")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrderForClient(@RequestBody CancelOrderDto reqDto) {
        clientOrderService.cancelOrderForClient(reqDto);
    }

    @PatchMapping("/complete-order")
    @ResponseStatus(HttpStatus.OK)
    public void completeOrderForClient(@RequestBody CompleteOrderDto reqDto) {
        clientOrderService.completeOrderForClient(reqDto);
    }
}
