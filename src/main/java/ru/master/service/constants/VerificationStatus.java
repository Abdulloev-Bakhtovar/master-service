package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerificationStatus {

    // Статусы номера телефона
    PHONE_NOT_VERIFIED("Номер не подтвержден"),

    // Статусы заполнения информации и документов
    INFO_NOT_ENTERED("Информация не введена"),

    // Статусы проверки
    UNDER_REVIEW("На проверке"),
    APPROVED("Проверка пройдена"),
    REJECTED("Отказано");

    private final String displayName;
}