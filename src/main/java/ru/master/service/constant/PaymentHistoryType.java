package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentHistoryType {

    CREDIT_ORDER("Зачисление за заказ"),
    CREDIT_TOP_UP("Пополнение счета вручную"),

    DEBIT_COMMISSION("Комиссия сервиса"),
    DEBIT_WITHDRAWAL("Вывод средств");

    private final String displayName;
}
