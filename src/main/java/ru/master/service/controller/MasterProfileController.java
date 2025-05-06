package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;
import ru.master.service.model.dto.request.MasterStatusUpdateDto;
import ru.master.service.model.dto.request.PostponeReqForMasterDto;
import ru.master.service.model.dto.response.*;
import ru.master.service.service.FileStorageService;
import ru.master.service.service.MasterProfileService;
import ru.master.service.service.MasterStatisticsService;
import ru.master.service.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/master-profiles")
@RequiredArgsConstructor
public class MasterProfileController {

    private final MasterProfileService masterProfileService;
    private final OrderService orderService;
    private final MasterStatisticsService masterStatisticsService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@ModelAttribute CreateMasterProfileReqDto reqDto) throws Exception {
        masterProfileService.create(reqDto);
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public MasterInfoForProfileResDto getMasterInfo() {
        return masterProfileService.getMasterInfo();
    }

    @GetMapping("/orders/available")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterAvailableOrdersResDto> getMasterAvailableOrders() {
        return orderService.getMasterAvailableOrders();
    }

    @GetMapping("/orders/completed")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterCompletedOrdersResDto> getMasterCompletedOrders() {
        return orderService.getMasterCompletedOrders();
    }

    @GetMapping("/orders/active")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterActiveOrdersResDto> getMasterActiveOrders() {
        return orderService.getMasterActiveOrders();
    }

    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailForMasterResDto getByIdForMaster(@PathVariable UUID orderId) {
        return orderService.getByIdForMaster(orderId);
    }

    @PatchMapping("/orders/{orderId}/accept")
    @ResponseStatus(HttpStatus.OK)
    public void acceptOrderForMaster(@PathVariable UUID orderId) {
        orderService.acceptOrderForMaster(orderId);
    }

    @PatchMapping("/orders/{orderId}/arrived")
    @ResponseStatus(HttpStatus.OK)
    public void arriveOrderForMaster(@PathVariable UUID orderId) {
        orderService.arriveOrderForMaster(orderId);
    }

    @PatchMapping("/orders/availability")
    @ResponseStatus(HttpStatus.OK)
    public void availabilityOrderForMaster() {
        orderService.availabilityOrderForMaster();
    }

    @PatchMapping("/orders/{orderId}/postpone")
    @ResponseStatus(HttpStatus.OK)
    public void postponeOrderForMaster(@PathVariable UUID orderId,
                                       @RequestBody PostponeReqForMasterDto reqDto) {
        orderService.postponeOrderForMaster(orderId, reqDto);
    }

    @PostMapping("/change-status")
    public void updateMasterStatus(@RequestBody MasterStatusUpdateDto reqDto) {
        masterProfileService.updateMasterStatus(reqDto);
    }

    @GetMapping("/status")
    public EnumResDto getMasterStatus() {
        return masterProfileService.getMasterStatus();
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public MasterStatisticsResDto getMasterStatistics() {
        return masterStatisticsService.getStatisticsForMaster();
    }

    @GetMapping("/documents")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ImageResDto>> getMasterDocuments() {
        List<ImageResDto> docs = fileStorageService.getMasterDocuments();
        return ResponseEntity.ok(docs);
    }

    @GetMapping("/photo-profile")
    public ResponseEntity<ImageResDto> getMasterPhotoProfile() {
        ImageResDto profileImage = fileStorageService.getMasterProfileImage();
        return ResponseEntity.ok(profileImage);
    }

}
