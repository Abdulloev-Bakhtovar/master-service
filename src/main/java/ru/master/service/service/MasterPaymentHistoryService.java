package ru.master.service.service;

import ru.master.service.model.Order;
import ru.master.service.model.dto.response.MasterPaymentHistoryResDto;

import java.util.List;

public interface MasterPaymentHistoryService {

    void create(Order order);

    List<MasterPaymentHistoryResDto> getCreditHistory();

    List<MasterPaymentHistoryResDto> getDebitHistory();
}
