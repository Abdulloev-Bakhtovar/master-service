package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.mapper.MasterFeedbackMapper;
import ru.master.service.model.Order;
import ru.master.service.model.dto.MasterFeedbackForCompleteOrderForClientDto;
import ru.master.service.repository.MasterFeedbackRepo;
import ru.master.service.service.MasterFeedbackService;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterFeedbackServiceImpl implements MasterFeedbackService {

    private final MasterFeedbackRepo masterFeedbackRepo;
    private final MasterFeedbackMapper masterFeedbackMapper;

    @Override
    public void create(MasterFeedbackForCompleteOrderForClientDto reqDto, Order order) {
        var masterFeedback = masterFeedbackMapper.toMasterFeedbackEntity(reqDto, order);
        masterFeedbackRepo.save(masterFeedback);
    }
}
