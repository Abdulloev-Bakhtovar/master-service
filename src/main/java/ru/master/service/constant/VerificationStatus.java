package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerificationStatus implements DisplayableEnum {

    PHONE_NOT_VERIFIED("Номер не подтвержден"),
    INFO_NOT_ENTERED("Информация не введена"),
    UNDER_REVIEW("На проверке"),
    APPROVED("Проверка пройдена"),
    REJECTED("Отказано");

    private final String displayName;
}