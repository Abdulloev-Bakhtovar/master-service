package ru.master.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.constant.DocumentType;
import ru.master.service.model.dto.request.CreateMasterProfileReqDto;
import ru.master.service.model.dto.request.MasterDocumentReqDto;
import ru.master.service.model.dto.request.MasterStatusUpdateDto;
import ru.master.service.model.dto.request.PostponeReqForMasterDto;
import ru.master.service.model.dto.response.*;
import ru.master.service.service.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/master-profiles")
@RequiredArgsConstructor
public class MasterProfileController {

    private final MasterProfileService masterProfileService;
    private final OrderService orderService;
    private final MasterStatisticsService masterStatisticsService;
    private final S3StorageService fileStorageService;
    private final MasterDocumentService masterDocumentService;

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

    @GetMapping("/{id}/photo-profile")
    public ResponseEntity<byte[]> getMasterPhotoProfile(@PathVariable UUID id) {
        ImageResDto image = fileStorageService.getMasterDocumentPhoto(DocumentType.PROFILE, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());

    }

    @GetMapping("/{id}/photo-passport-main")
    public ResponseEntity<byte[]> getMasterPhotoPassportMain(@PathVariable UUID id) {
        ImageResDto image = fileStorageService.getMasterDocumentPhoto(DocumentType.PASSPORT_MAIN, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());

    }

    @GetMapping("/{id}/photo-passport-register")
    public ResponseEntity<byte[]> getMasterPhotoPassportRegister(@PathVariable UUID id) {
        ImageResDto image = fileStorageService.getMasterDocumentPhoto(DocumentType.PASSPORT_REGISTRATION, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());

    }

    @GetMapping("/{id}/photo-snils")
    public ResponseEntity<byte[]> getMasterPhotoSnils(@PathVariable UUID id) {
        ImageResDto image = fileStorageService.getMasterDocumentPhoto(DocumentType.SNILS, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());

    }

    @GetMapping("/{id}/photo-inn")
    public ResponseEntity<byte[]> getMasterPhotoInn(@PathVariable UUID id) {
        ImageResDto image = fileStorageService.getMasterDocumentPhoto(DocumentType.INN, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getData());

    }

    @PostMapping(value = "/add-other-document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addOtherDocument(@ModelAttribute MasterDocumentReqDto reqDto) throws IOException {
        masterDocumentService.add(reqDto);
    }

    @GetMapping("/{masterId}/other-documents")
    public List<MasterDocumentResDto> getAllDocuments(@PathVariable UUID masterId) {
        return masterDocumentService.getAll(masterId);
    }

    @GetMapping("/other-documents/{id}/file")
    public ResponseEntity<byte[]> getOtherDocument(@PathVariable UUID id) {
        ImageResDto file = fileStorageService.getOtherDocument(DocumentType.OTHER, id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getData());
    }

}
