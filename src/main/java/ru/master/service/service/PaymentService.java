package ru.master.service.service;

import ru.master.service.model.dto.request.PaymentReqDto;
import ru.master.service.model.dto.response.PaymentResDto;

public interface PaymentService {

    PaymentResDto createPayment(PaymentReqDto paymentRequest);

    PaymentResDto checkPaymentStatus(String paymentId);

    void updatePaymentStatus(String paymentId, String status);

    void isValid(String rawBody, String signatureHeader);
}
