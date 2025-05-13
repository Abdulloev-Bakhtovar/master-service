package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientPointType implements DisplayableEnum {

    REFERRAL_REGISTRATION("Реферальный"),
    REFERRAL_FIRST_ORDER("Реферальный (первая покупка)");

    private final String displayName;
}

