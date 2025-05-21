package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MasterDocumentType implements DisplayableEnum {

    DIPLOM("Диплом"),
    CERTIFICATE("Сертификат"),
    OTHER("Другое");

    private final String displayName;
}
