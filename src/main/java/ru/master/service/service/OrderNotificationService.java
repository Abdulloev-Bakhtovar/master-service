package ru.master.service.service;

import ru.master.service.model.Order;

public interface OrderNotificationService {

    void notifyMasters(Order order);
}
