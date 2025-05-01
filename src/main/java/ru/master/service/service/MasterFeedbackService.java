package ru.master.service.service;

import ru.master.service.model.ClientOrder;
import ru.master.service.model.dto.MasterFeedbackForClientOrderDto;

public interface MasterFeedbackService {

    void create(MasterFeedbackForClientOrderDto reqDto, ClientOrder order);
}
