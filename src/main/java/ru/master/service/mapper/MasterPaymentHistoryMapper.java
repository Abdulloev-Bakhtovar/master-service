package ru.master.service.mapper;

import ru.master.service.model.MasterPaymentHistory;
import ru.master.service.model.dto.response.MasterPaymentHistoryResDto;

public interface MasterPaymentHistoryMapper {

    MasterPaymentHistoryResDto toDto(MasterPaymentHistory entity);
}
