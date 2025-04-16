package ru.master.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaritalStatus {

    MARRIED("Женат/Замужем"),
    SINGLE("Холост/Не замужем"),
    DIVORCED("Разведен/Разведена");

    private final String displayName;
}
