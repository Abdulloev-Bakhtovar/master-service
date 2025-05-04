package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MasterStatus implements DisplayableEnum {

    WAITING_FOR_ORDERS("Жду заявок"),
    ON_ORDER("На заявке"),
    OFFLINE("Не в сети");

    private final String displayName;
}
