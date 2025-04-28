package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceRequestStatus {

    SEARCH_MASTER("Поиск мастера"),
    IN_PROGRESS("Взята в работу"),
    COMPLETED("Выполнен"),
    CANCELLED("Отменён");

    private final String displayName;
}
