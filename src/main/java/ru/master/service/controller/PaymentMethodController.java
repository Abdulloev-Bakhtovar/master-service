package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.PayMethodReqDto;
import ru.master.service.model.dto.response.PayMethodResDto;
import ru.master.service.service.PaymentMethodService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping("/payment-method")
    public PayMethodResDto getCurrentPaymentMethod() {
        return paymentMethodService.getCurrentPaymentMethod();
    }

    @PatchMapping("/admin/payment-method/update")
    public void updatePaymentMethod(@RequestBody PayMethodReqDto dto) {
        paymentMethodService.update(dto);
    }
}
