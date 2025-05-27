package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentPurpose {

    ORDER_PAYMENT,
    MASTER_TOP_UP
}
