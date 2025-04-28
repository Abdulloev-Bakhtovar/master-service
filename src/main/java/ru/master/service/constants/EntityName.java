package ru.master.service.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EntityName {

    USER("User"),
    MASTER_PROFILE("Master profile"),
    CITY("City"),
    DOCUMENT("Document"),
    SERVICE_CATEGORY("Service category"),
    SUB_SERVICE_CATEGORY("Sub service category"),
    SERVICE_REQUEST("Service request"),;

    private final String displayName;

    public String get() {
        return displayName;
    }
}
