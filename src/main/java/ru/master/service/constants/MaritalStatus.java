package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaritalStatus implements DisplayableEnum {

    MARRIED("Женат/Замужем"),
    SINGLE("Холост/Не замужем"),
    DIVORCED("Разведен/Разведена");

    private final String displayName;
}
