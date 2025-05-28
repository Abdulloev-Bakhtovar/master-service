package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.OrderMapper;
import ru.master.service.model.dto.response.IdDto;
import ru.master.service.repository.OrderRepo;
import ru.master.service.service.OrderNotificationService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderNotificationServiceImpl implements OrderNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;

    @Override
    public void notifyMasters(IdDto reqDto) {
        var order = orderRepo.findById(reqDto.getId())
                .orElseThrow(() -> new AppException(
                        "Order not found",
                        HttpStatus.NOT_FOUND
                ));
        var dto = orderMapper.toAllAvailableOrderForMasterDto(order);

        messagingTemplate.convertAndSend(
                "/topic/master-profiles/orders/" + order.getCity().getId(),
                dto,
                Map.of("content-type", "application/json")
        );
    }
}
