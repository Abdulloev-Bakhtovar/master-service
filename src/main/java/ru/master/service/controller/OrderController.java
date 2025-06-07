package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.admin.model.dto.responce.AdminOrderSummaryResDto;
import ru.master.service.model.dto.request.*;
import ru.master.service.model.dto.response.*;
import ru.master.service.service.OrderNotificationService;
import ru.master.service.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderNotificationService orderNotificationService;

    /********************** MASTER START ************************/

    @GetMapping("/master-profiles/orders/available")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterAvailableOrdersResDto> getMasterAvailableOrders() {
        return orderService.getMasterAvailableOrders();
    }

    @GetMapping("/master-profiles/orders/completed")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterCompletedOrdersResDto> getMasterCompletedOrders() {
        return orderService.getMasterCompletedOrders();
    }

    @GetMapping("/master-profiles/orders/active")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterActiveOrdersResDto> getMasterActiveOrders() {
        return orderService.getMasterActiveOrders();
    }

    @GetMapping("/master-profiles/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailForMasterResDto getByIdForMaster(@PathVariable UUID orderId) {
        return orderService.getByIdForMaster(orderId);
    }

    @PatchMapping("/master-profiles/orders/{orderId}/accept")
    @ResponseStatus(HttpStatus.OK)
    public void acceptOrderForMaster(@PathVariable UUID orderId) {
        orderService.acceptOrderForMaster(orderId);
    }

    @PatchMapping("/master-profiles/orders/{orderId}/arrived")
    @ResponseStatus(HttpStatus.OK)
    public void arriveOrderForMaster(@PathVariable UUID orderId) {
        orderService.arriveOrderForMaster(orderId);
    }

    @PatchMapping("/master-profiles/orders/availability")
    @ResponseStatus(HttpStatus.OK)
    public void availabilityOrderForMaster() {
        orderService.availabilityOrderForMaster();
    }

    @PatchMapping("/master-profiles/orders/{orderId}/postpone")
    @ResponseStatus(HttpStatus.OK)
    public void postponeOrderForMaster(@PathVariable UUID orderId,
                                       @RequestBody PostponeReqForMasterDto reqDto) {
        orderService.postponeOrderForMaster(orderId, reqDto);
    }

    /********************** CLIENT START ************************/

    @GetMapping("/client-profiles/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<AllClientOrderResDto> getAllClientOrders() {
        return orderService.getAllClientOrders();
    }

    @PostMapping("/client-profiles/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto createOrder(@RequestBody CreateOrderReqDto reqDto) {
        var orderId =  orderService.create(reqDto);

        orderNotificationService.notifyMasters(orderId);
        return orderId;
    }

    @PatchMapping("/client-profiles/orders/{orderId}/price")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderPrice(@PathVariable("orderId") UUID orderId, @RequestBody UpdateOrderPriceReqDto reqDto) {
        orderService.updateOrderPrice(orderId, reqDto);
    }

    @GetMapping("/client-profiles/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailForClientResDto getByIdForClient(@PathVariable UUID orderId) {
        return orderService.getByIdForClient(orderId);
    }

    @PatchMapping("/client-profiles/orders/{orderId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrderForClient(@PathVariable UUID orderId, @RequestBody CancelOrderForClientDto reqDto) {
        orderService.cancelOrderForClient(orderId, reqDto);
    }

    @PatchMapping("/client-profiles/orders/{orderId}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeOrderForClient(@PathVariable UUID orderId, @RequestBody CompleteOrderForClientDto reqDto) {
        orderService.completeOrderForClient(orderId, reqDto);
    }

    @PostMapping("/client-profiles/orders/{id}/payments")
    public PaymentResDto createPayment(@PathVariable UUID id) {
        return orderService.createPaymentForOrder(id);
    }

    /********************** ADMIN START ************************/

    @GetMapping("/admin/orders/summary")
    public AdminOrderSummaryResDto getOrderSummary() {
        return orderService.getAdminOrderSummary();
    }

    //изменит способ оплату для определённый заказ
    @PatchMapping("/admin/orders/{orderId}/payment-method")
    public void choosePaymentMethod(@PathVariable UUID orderId,
                                    @RequestBody ChoosePaymentMethodReqDto dto) {
        orderService.choosePaymentMethod(orderId, dto);
    }
}
