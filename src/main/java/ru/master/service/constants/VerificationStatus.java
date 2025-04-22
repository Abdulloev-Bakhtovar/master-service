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
    INFO_ENTERED("Информация введена"),

    // Статусы документов
   DOCS_UPLOADED("Документы загружены"),

    // Статусы проверки
    UNDER_REVIEW("На проверке"),
    APPROVED("Проверка пройдена"),
    REJECTED("Отказано");

    private final String displayName;
}