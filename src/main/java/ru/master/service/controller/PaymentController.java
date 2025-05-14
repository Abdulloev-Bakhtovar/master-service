package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.PaymentReqDto;
import ru.master.service.model.dto.response.PaymentResDto;
import ru.master.service.service.PaymentService;

import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public PaymentResDto createPayment(@RequestBody PaymentReqDto request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<?> checkPaymentStatus(@PathVariable String paymentId) {
        try {
            PaymentResDto payment = paymentService.checkPaymentStatus(paymentId);
            return null;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    // Webhook endpoint остается без изменений
    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload,
                                              @RequestHeader(value = "Content-HMAC", required = false) String signatureHeader) {

        if (signatureHeader != null) {
            String rawBody = (String) payload.get("paymentId");
            paymentService.isValid(rawBody,signatureHeader);
        }

        // 1. Вытащим из webhook'а paymentId
        Map<String, Object> object = (Map<String, Object>) payload.get("object");
        String paymentId = (String) object.get("id");
        String status = (String) object.get("status");

        System.out.println(paymentId + " : " + status);
        paymentService.updatePaymentStatus(paymentId, status);

        return ResponseEntity.ok().build();
    }
}