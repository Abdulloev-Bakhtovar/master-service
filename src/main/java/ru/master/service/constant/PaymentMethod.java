package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod implements DisplayableEnum {

    ONLINE("Онлайн оплата"),
    CASH("Оплата наличными");

    private final String displayName;
}
