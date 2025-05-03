package ru.master.service.mapper;

import ru.master.service.model.MasterFeedback;
import ru.master.service.model.Order;
import ru.master.service.model.dto.MasterFeedbackForCompleteOrderForClientDto;

public interface MasterFeedbackMapper {

    MasterFeedbackForCompleteOrderForClientDto toOrderInfoForClientDto(MasterFeedback masterFeedback);

    MasterFeedback toMasterFeedbackEntity(MasterFeedbackForCompleteOrderForClientDto reqDto, Order order);
}
