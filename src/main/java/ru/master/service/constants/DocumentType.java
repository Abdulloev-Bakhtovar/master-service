package ru.master.service.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentType {

    PROFILE("profile", "users/profile"),
    PASSPORT_MAIN("passport_main", "users/passport"),
    PASSPORT_REGISTRATION("passport_registration", "users/passport"),
    SNILS("snils", "users/snils"),
    INN("inn", "users/inn"),
    NEWS_PHOTO("news_photo", "news");

    private final String prefix;
    private final String subDirectory;
}