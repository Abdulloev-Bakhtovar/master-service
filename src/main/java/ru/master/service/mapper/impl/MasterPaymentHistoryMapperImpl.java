package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.MasterPaymentHistoryMapper;
import ru.master.service.model.MasterPaymentHistory;
import ru.master.service.model.dto.response.MasterPaymentHistoryResDto;

@Component
public class MasterPaymentHistoryMapperImpl implements MasterPaymentHistoryMapper {

    @Override
    public MasterPaymentHistoryResDto toDto(MasterPaymentHistory entity) {
        if (entity == null) return null;

        return MasterPaymentHistoryResDto.builder()
                .id(entity.getId())
                .type(entity.getType())
                .amount(entity.getAmount())
                .orderId(entity.getOrder().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
