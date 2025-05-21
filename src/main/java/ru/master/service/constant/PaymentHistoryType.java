package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentHistoryType {

    CREDIT("Зачисление"),
    DEBIT("Списание");

    private final String displayName;
}
