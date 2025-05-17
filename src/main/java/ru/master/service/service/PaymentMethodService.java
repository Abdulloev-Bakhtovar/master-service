package ru.master.service.service;

import ru.master.service.constant.PayMethod;
import ru.master.service.model.dto.response.PayMethodResDto;

public interface PaymentMethodService {

    void update(PayMethod payMethod);

    PayMethodResDto getCurrentPaymentMethod();
}
