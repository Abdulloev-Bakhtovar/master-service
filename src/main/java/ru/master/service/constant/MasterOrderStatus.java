package ru.master.service.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MasterOrderStatus implements DisplayableEnum {

    SEARCHING_FOR_MASTER("В поиске мастера"),
    TAKEN_IN_WORK("Взята в работу"),
    ARRIVED_AT_CLIENT("Прибыл к клиенту"),
    READY_FOR_REQUESTS("Готова принимать заявки"),
    DEFERRED_REPAIR("Отложенный ремонт"),
    CANCELLED("Отменит заказ"),
    COMPLETED("Выполнен");

    private final String displayName;
}
