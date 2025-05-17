package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.exception.AppException;
import ru.master.service.model.dto.AdminForPayMethodResDto;
import ru.master.service.model.dto.request.PayMethodReqDto;
import ru.master.service.model.dto.response.PayMethodResDto;
import ru.master.service.repository.PaymentMethodRepo;
import ru.master.service.service.PaymentMethodService;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepo paymentMethodRepo;

    @Override
    public void update(PayMethodReqDto reqDto) {

        var payEntities = paymentMethodRepo.findAll();

        for (var payEntity : payEntities) {
            payEntity.setValue(reqDto.getPayMethod());
        }

        paymentMethodRepo.saveAll(payEntities);
    }

    @Override
    public PayMethodResDto getCurrentPaymentMethod() {
        var pay = paymentMethodRepo.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new AppException(
                        "No payment method configured",
                        HttpStatus.NOT_FOUND
                ));

        var adminDto = AdminForPayMethodResDto.builder()
                .id(pay.getAdmin().getId())
                .name(pay.getAdmin().getName())
                .build();

        return PayMethodResDto.builder()
                .id(pay.getId())
                .value(pay.getValue())
                .adminDto(adminDto)
                .createdAt(pay.getCreatedAt())
                .updatedAt(pay.getUpdatedAt())
                .build();
    }
}
