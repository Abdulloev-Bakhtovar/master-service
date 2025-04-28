package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceRequestStatus {

    SEARCH_MASTER("Поиск мастера");

    private final String displayName;
}
