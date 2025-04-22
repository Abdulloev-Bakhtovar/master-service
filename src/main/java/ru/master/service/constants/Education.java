package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Education implements DisplayableEnum {

    HIGHER("Высшее"),
    SECONDARY("Среднее"),
    SECONDARY_SPECIAL("Среднее специальное"),
    INCOMPLETE_HIGHER("Неоконченное высшее");

    private final String displayName;
}
