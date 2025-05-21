package ru.master.service.service;

import ru.master.service.model.dto.request.PayMethodReqDto;
import ru.master.service.model.dto.response.PayMethodResDto;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService {

    List<PayMethodResDto> getAll();

    List<PayMethodResDto> getAllVisible();

    void create(PayMethodReqDto reqDto);

    void changeVisibility(UUID id, boolean isVisible);
}
