package ru.master.service.service;

import ru.master.service.model.dto.request.PayMethodReqDto;
import ru.master.service.model.dto.response.PayMethodResDto;

public interface PaymentMethodService {

    void update(PayMethodReqDto reqDto);

    PayMethodResDto getCurrentPaymentMethod();
}
