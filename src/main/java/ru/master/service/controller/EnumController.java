package ru.master.service.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.constant.*;
import ru.master.service.service.EnumService;

import java.util.List;

@PermitAll
@RestController
@RequestMapping("/enums")
@RequiredArgsConstructor
public class EnumController {

    private final EnumService enumService;

    @GetMapping("/education")
    public List<EnumResDto> getEducation() {
        return enumService.getAllValues(Education.class);
    }

    @GetMapping("/marital-status")
    public List<EnumResDto> getMaritalStatus() {
        return enumService.getAllValues(MaritalStatus.class);
    }

    @GetMapping("/role")
    public List<EnumResDto> getRoles() {
        return enumService.getAllValues(Role.class);
    }

    @GetMapping("/service-type")
    public List<EnumResDto> getServiceType() {
        return enumService.getAllValues(ServiceType.class);
    }

    @GetMapping("/client-order-status")
    public List<EnumResDto> getClientOrderStatus() {
        return enumService.getAllValues(ClientOrderStatus.class);
    }

    @GetMapping("/master-order-status")
    public List<EnumResDto> getMasterOrderStatus() {
        return enumService.getAllValues(MasterOrderStatus.class);
    }

    @GetMapping("/master-status")
    public List<EnumResDto> getMasterStatus() {
        return enumService.getAllValues(MasterStatus.class);
    }

    @GetMapping("/payment-method")
    public List<EnumResDto> getPaymentMethod() {
        return enumService.getAllValues(PayMethod.class);
    }
}