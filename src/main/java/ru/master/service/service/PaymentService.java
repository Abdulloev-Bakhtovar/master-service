package ru.master.service.service;

import ru.master.service.model.MasterProfile;
import ru.master.service.model.Order;
import ru.master.service.model.dto.request.PaymentReqDto;
import ru.master.service.model.dto.request.YookassaWebhookDto;
import ru.master.service.model.dto.response.PaymentResDto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PaymentService {

    PaymentResDto createPayment(PaymentReqDto paymentRequest, Order order, MasterProfile master);

    PaymentResDto createTopUpPayment(PaymentReqDto request);

    PaymentResDto checkAndUpdatePaymentStatus(String paymentId);

    void updatePaymentStatus(YookassaWebhookDto reqDto);

    String computeHmacSHA256(String payload) throws NoSuchAlgorithmException, InvalidKeyException;
}
