package ru.master.service.service;

import ru.master.service.model.Order;
import ru.master.service.model.dto.MasterFeedbackForCompleteOrderForClientDto;

public interface MasterFeedbackService {

    void create(MasterFeedbackForCompleteOrderForClientDto reqDto, Order order);
}
