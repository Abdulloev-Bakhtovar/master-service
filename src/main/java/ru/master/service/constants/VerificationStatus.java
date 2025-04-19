package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerificationStatus {

    // Статусы номера телефона
    PHONE_NOT_VERIFIED("Номер не подтвержден"),
    PHONE_VERIFIED("Номер подтвержден"),

    // Статусы заполнения информации
    INFO_NOT_ENTERED("Информация не введена"),
    INFO_ENTERED("Информация введена"),

    // Статусы документов
    DOCS_NOT_UPLOADED("Документы не загружены"),
    DOCS_PARTIALLY_UPLOADED("Документы загружены частично"),
    DOCS_UPLOADED("Документы загружены"),

    // Статусы проверки
    UNDER_REVIEW("На проверке"),
    APPROVED("Проверка пройдена"),
    REJECTED("Отказано"),
    NEEDS_CORRECTION("Требуются исправления");

    private final String displayName;
}