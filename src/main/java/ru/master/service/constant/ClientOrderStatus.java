package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClientOrderStatus implements DisplayableEnum {

    SEARCHING_FOR_MASTER("В поиске мастера"),
    TAKEN_IN_WORK("Взята в работу"),
    COMPLETED("Завершит заказ"),
    CANCELLED("Отменит заказ"),
    FINISHED("Выполнен");

    private final String displayName;
}
