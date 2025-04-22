package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements DisplayableEnum {

    MASTER("Мастер"),
    CLIENT("Клиент"),
    ADMIN("Админ");

    private final String displayName;
}