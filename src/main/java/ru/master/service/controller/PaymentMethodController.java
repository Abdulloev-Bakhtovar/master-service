package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.PayMethodReqDto;
import ru.master.service.model.dto.response.PayMethodResDto;
import ru.master.service.service.PaymentMethodService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    /********************** ADMIN START ************************/

    @GetMapping("/admin/payment-methods")
    public List<PayMethodResDto> getAll() {
        return paymentMethodService.getAll();
    }

    @PostMapping("/admin/payment-methods")
    public void create(@RequestBody PayMethodReqDto dto) {
        paymentMethodService.create(dto);
    }

    @PatchMapping("/admin/payment-methods/{id}/hidden")
    public void hideMegapixel(@PathVariable UUID id) {
        paymentMethodService.changeVisibility(id, false);
    }

    @PatchMapping("/admin/payment-methods/{id}/visible")
    public void showMegapixel(@PathVariable UUID id) {
        paymentMethodService.changeVisibility(id, true);
    }

    /********************** CLIENT START ************************/

    @GetMapping("/payment-methods")
    public List<PayMethodResDto> getAllVisible() {
        return paymentMethodService.getAllVisible();
    }
}
