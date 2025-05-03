package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CreateOrderReqDto;
import ru.master.service.model.dto.response.IdDto;
import ru.master.service.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdDto create(@RequestBody CreateOrderReqDto reqDto) {
        return orderService.create(reqDto);
    }

}
