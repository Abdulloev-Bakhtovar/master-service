package ru.master.service.service;

import ru.master.service.model.dto.response.IdDto;

public interface OrderNotificationService {

    void notifyMasters(IdDto reqDto);
}
