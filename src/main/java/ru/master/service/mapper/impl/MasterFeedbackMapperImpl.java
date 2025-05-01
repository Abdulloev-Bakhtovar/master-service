package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.MasterFeedbackMapper;
import ru.master.service.model.ClientOrder;
import ru.master.service.model.MasterFeedback;
import ru.master.service.model.dto.MasterFeedbackForClientOrderDto;

@Component
public class MasterFeedbackMapperImpl implements MasterFeedbackMapper {

    @Override
    public MasterFeedbackForClientOrderDto toOrderInfoForClientDto(MasterFeedback entity) {
        if (entity == null) return null;

        return MasterFeedbackForClientOrderDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .rating(entity.getRating())
                .review(entity.getReview())
                .build();
    }

    @Override
    public MasterFeedback toEntity(MasterFeedbackForClientOrderDto reqDto, ClientOrder order) {
        if (reqDto == null) return null;
        if (order == null) return null;

        return MasterFeedback.builder()
                .id(reqDto.getId())
                .review(reqDto.getReview())
                .rating(reqDto.getRating())
                .master(order.getMasterProfile())
                .order(order)
                .build();
    }
}
