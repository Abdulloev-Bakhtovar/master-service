package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType implements DisplayableEnum {

    REPAIR("Ремонт"),
    INSTALLATION("Установка");

    private final String displayName;
}
