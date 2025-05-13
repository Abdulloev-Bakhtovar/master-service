package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CertificateStatus implements DisplayableEnum {

    ACTIVE("Активный"),
    USED("Использованный"),
    EXPIRED("Истёкший");

    private final String displayName;
}
