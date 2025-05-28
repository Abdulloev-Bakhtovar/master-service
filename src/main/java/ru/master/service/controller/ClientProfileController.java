package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.*;
import ru.master.service.model.dto.response.*;
import ru.master.service.service.ClientProfileService;
import ru.master.service.service.OrderNotificationService;
import ru.master.service.service.OrderService;
import ru.master.service.service.ReferralProgramService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client-profiles")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;
    private final OrderService orderService;
    private final ReferralProgramService referralProgramService;
    private final OrderNotificationService orderNotificationService;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ClientInfoForCreateOrderResDto getClientInfo() {
        return clientProfileService.getClientInfo();
    }

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

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createOrder(@RequestBody CreateOrderReqDto reqDto) {
        var orderId =  orderService.create(reqDto);

        orderNotificationService.notifyMasters(orderId);
        return orderId;
    }

    @PatchMapping("/orders/{orderId}/price")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderPrice(@PathVariable("orderId") UUID orderId, @RequestBody UpdateOrderPriceReqDto reqDto) {
        orderService.updateOrderPrice(orderId, reqDto);
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

    @GetMapping("/referral/info")
    public ClientReferralInfoResDto getReferralInfo() {
        return referralProgramService.getReferralInfo();
    }

    @PostMapping("/orders/{id}/payments")
    public PaymentResDto createPayment(@PathVariable UUID id) {
        return orderService.createPaymentForOrder(id);
    }
}
