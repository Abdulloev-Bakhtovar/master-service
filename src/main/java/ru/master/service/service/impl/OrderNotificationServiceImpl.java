package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.master.service.mapper.OrderMapper;
import ru.master.service.model.Order;
import ru.master.service.service.OrderNotificationService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderNotificationServiceImpl implements OrderNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final OrderMapper orderMapper;

    @Override
    public void notifyMasters(Order order) {
        var dto = orderMapper.toAllAvailableOrderForMasterDto(order);

        messagingTemplate.convertAndSend(
                "/topic/master-profiles/orders/" + order.getCity().getId(),
                dto,
                Map.of("content-type", "application/json")
        );
    }
}
