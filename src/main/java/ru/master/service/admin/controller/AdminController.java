package ru.master.service.admin.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.master.service.admin.model.dto.EmailDto;
import ru.master.service.admin.model.dto.request.CreateAdminProfileReqDto;
import ru.master.service.admin.model.dto.request.LoginAdminProfileReqDto;
import ru.master.service.admin.model.dto.request.ResetPasswordReqDto;
import ru.master.service.admin.model.dto.responce.AdminOrderSummaryResDto;
import ru.master.service.admin.service.AdminProfileService;
import ru.master.service.auth.model.dto.response.TokenDto;
import ru.master.service.model.dto.request.ChoosePaymentMethodReqDto;
import ru.master.service.service.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminProfileService adminProfileService;
    private final OrderService orderService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateAdminProfileReqDto reqDto) {
        adminProfileService.create(reqDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@RequestBody LoginAdminProfileReqDto reqDto) {
        return adminProfileService.login(reqDto);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestBody TokenDto tokenDto) {
        adminProfileService.logout(tokenDto);
    }

    @PatchMapping("/password/reset-request")
    @ResponseStatus(HttpStatus.OK)
    public void resetPasswordRequest(@RequestBody EmailDto reqDto) throws MessagingException {
        adminProfileService.resetPasswordRequest(reqDto);
    }

    @PatchMapping("/password/reset-confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmResetPassword(@RequestBody ResetPasswordReqDto resetPasswordReqDto) {
        adminProfileService.confirmTokenFromEmailAndResetPass(resetPasswordReqDto);
    }

    @GetMapping("/orders/summary")
    public AdminOrderSummaryResDto getOrderSummary() {
        return orderService.getAdminOrderSummary();
    }

    @PatchMapping("/orders/{orderId}/payment-method")
    public void choosePaymentMethod(@PathVariable("orderId") UUID orderId,
                                    @RequestBody ChoosePaymentMethodReqDto dto) {
        orderService.choosePaymentMethod(orderId, dto);
    }
}
