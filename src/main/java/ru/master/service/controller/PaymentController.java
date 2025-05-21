package ru.master.service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.YookassaWebhookDto;
import ru.master.service.model.dto.response.PaymentResDto;
import ru.master.service.service.PaymentService;
import ru.master.service.util.IpUtil;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/status/{paymentId}")
    public PaymentResDto checkPaymentStatus(@PathVariable String paymentId) {
        try {
            // start test
            YookassaWebhookDto dto = new YookassaWebhookDto();
            var res = paymentService.checkPaymentStatus(paymentId);
            dto.getObject().setPaid(res.isPaid());
            dto.getObject().setStatus(res.getStatus());
            paymentService.updatePaymentStatus(dto);
            //end test
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    // Webhook endpoint остается без изменений
    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody YookassaWebhookDto dto,
                              HttpServletRequest request
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        // start test
        var res = paymentService.checkPaymentStatus(dto.getObject().getId());
        dto.getObject().setPaid(res.isPaid());
        dto.getObject().setStatus(res.getStatus());
        paymentService.updatePaymentStatus(dto);
        //end test


        /*String ip = request.getRemoteAddr(); // Получаем IP

        if (!isAllowedYookassaIp(ip)) {
            throw new AppException("Unauthorized IP", HttpStatus.FORBIDDEN);
        }

        String signature = request.getHeader("Content-HMAC");

        if (signature != null) {
            String payload = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            String computedSignature = paymentService.computeHmacSHA256(payload);

            if (!computedSignature.equals(signature)) {
                throw new AppException("Unauthorized IP", HttpStatus.FORBIDDEN);
            }
        }
        paymentService.updatePaymentStatus(dto);*/
    }

    private boolean isAllowedYookassaIp(String ip) {
        List<String> allowedCidrs = List.of(
                "185.71.76.0/27",
                "185.71.77.0/27",
                "77.75.153.0/25",
                "77.75.156.11/32",
                "77.75.156.35/32",
                "77.75.154.128/25",
                "2a02:5180::/32"
        );
        return allowedCidrs.stream().anyMatch(cidr -> IpUtil.isIpInCidr(ip, cidr));
    }
}