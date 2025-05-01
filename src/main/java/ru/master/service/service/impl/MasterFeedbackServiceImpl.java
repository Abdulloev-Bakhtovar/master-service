package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.mapper.MasterFeedbackMapper;
import ru.master.service.model.ClientOrder;
import ru.master.service.model.dto.MasterFeedbackForClientOrderDto;
import ru.master.service.repository.MasterFeedbackRepo;
import ru.master.service.service.MasterFeedbackService;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterFeedbackServiceImpl implements MasterFeedbackService {

    private final MasterFeedbackRepo masterFeedbackRepo;
    private final MasterFeedbackMapper masterFeedbackMapper;

    @Override
    public void create(MasterFeedbackForClientOrderDto reqDto, ClientOrder order) {
        var masterFeedback = masterFeedbackMapper.toEntity(reqDto, order);
        masterFeedbackRepo.save(masterFeedback);
    }
}
