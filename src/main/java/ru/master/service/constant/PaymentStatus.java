package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus implements DisplayableEnum {

    WAITING_FOR_CAPTURE("Ожидает подтверждения"),
    SUCCEEDED("Платёж успешно завершён"),
    CANCELED("Платёж отменён или ошибка"),
    REFUND_SUCCEEDED("Возврат успешно выполнен");

    private final String displayName;
}

