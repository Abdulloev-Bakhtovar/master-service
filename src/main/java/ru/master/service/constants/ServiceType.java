package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {

    REPAIR("Ремонт"),
    INSTALLATION("Установка");

    private final String displayName;
}
