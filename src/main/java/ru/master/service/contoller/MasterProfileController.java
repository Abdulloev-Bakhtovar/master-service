package ru.master.service.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.master.service.model.dto.request.CreateMasterProfileDto;
import ru.master.service.model.dto.response.AllMasterProfileOrderDto;
import ru.master.service.model.dto.response.AvailableOrdersForMasterDto;
import ru.master.service.model.dto.response.OrderInfoForMasterDto;
import ru.master.service.service.ClientOrderService;
import ru.master.service.service.MasterProfileService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/master-profiles")
@RequiredArgsConstructor
public class MasterProfileController {

    private final MasterProfileService masterProfileService;
    private final ClientOrderService clientOrderService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createMasterProfile(@ModelAttribute CreateMasterProfileDto reqDto) throws Exception {
        masterProfileService.create(reqDto);
    }

    @GetMapping("/my-completed-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<AllMasterProfileOrderDto> getMasterCompletedOrders() {
        return clientOrderService.getMasterCompletedOrders();
    }

    @GetMapping("/my-active-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<AllMasterProfileOrderDto> getMasterActiveOrders() {
        return clientOrderService.getMasterActiveOrders();
    }

    @GetMapping("/available-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableOrdersForMasterDto> getAvailableOrdersForMaster() {
        return clientOrderService.getAvailableOrdersForMaster();
    }

    @GetMapping("/order-info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderInfoForMasterDto getById(@PathVariable UUID id) {
        return clientOrderService.getOrderInfoForMaster(id);
    }

    @PatchMapping("/{orderId}/accept")
    @ResponseStatus(HttpStatus.OK)
    public void acceptOrderForMaster(@PathVariable UUID orderId) {
        clientOrderService.acceptOrderForMaster(orderId);
    }
}
