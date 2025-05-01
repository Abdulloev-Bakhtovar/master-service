package ru.master.service.mapper;

import ru.master.service.model.ClientOrder;
import ru.master.service.model.MasterFeedback;
import ru.master.service.model.dto.MasterFeedbackForClientOrderDto;

public interface MasterFeedbackMapper {

    MasterFeedbackForClientOrderDto toOrderInfoForClientDto(MasterFeedback masterFeedback);

    MasterFeedback toEntity(MasterFeedbackForClientOrderDto reqDto, ClientOrder order);
}
