package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.model.dto.MasterFeedbackForCompleteOrderForClientDto;
import ru.master.service.mapper.MasterFeedbackMapper;
import ru.master.service.model.MasterFeedback;
import ru.master.service.model.Order;

@Component
public class MasterFeedbackMapperImpl implements MasterFeedbackMapper {

    @Override
    public MasterFeedbackForCompleteOrderForClientDto toOrderInfoForClientDto(MasterFeedback entity) {
        if (entity == null) return null;

        return MasterFeedbackForCompleteOrderForClientDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .rating(entity.getRating())
                .review(entity.getReview())
                .build();
    }

    @Override
    public MasterFeedback toMasterFeedbackEntity(MasterFeedbackForCompleteOrderForClientDto reqDto, Order order) {
        if (reqDto == null) return null;

        return MasterFeedback.builder()
                .id(reqDto.getId())
                .review(reqDto.getReview())
                .rating(reqDto.getRating())
                .master(order.getMasterProfile())
                .order(order)
                .build();
    }
}
