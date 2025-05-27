package ru.master.service.service;

import ru.master.service.model.Order;
import ru.master.service.model.Payment;
import ru.master.service.model.dto.response.MasterPaymentHistoryResDto;

import java.util.List;

public interface MasterPaymentHistoryService {

    void createForOrder(Order order);

    void createForTopUp(Payment topUpMaster);

    List<MasterPaymentHistoryResDto> getCreditHistory();

    List<MasterPaymentHistoryResDto> getDebitHistory();
}
