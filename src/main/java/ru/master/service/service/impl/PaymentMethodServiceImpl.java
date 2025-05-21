package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.admin.repository.AdminProfileRepo;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.exception.AppException;
import ru.master.service.model.PaymentMethod;
import ru.master.service.model.dto.request.PayMethodReqDto;
import ru.master.service.model.dto.response.PayMethodResDto;
import ru.master.service.repository.PaymentMethodRepo;
import ru.master.service.service.PaymentMethodService;
import ru.master.service.util.AuthUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepo paymentMethodRepo;
    private final AuthUtil authUtil;
    private final AdminProfileRepo adminProfileRepo;

    @Override
    public List<PayMethodResDto> getAll() {
        var pays = paymentMethodRepo.findAll();

        return getPayMethodResDtos(pays);
    }

    @Override
    public List<PayMethodResDto> getAllVisible() {
        var pays = paymentMethodRepo.findByIsVisibleTrue();

        return getPayMethodResDtos(pays);
    }

    @Override
    public void create(PayMethodReqDto reqDto) {

        var adminId = authUtil.getAuthenticatedAdmin().getId();

        var admin = adminProfileRepo.findById(adminId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ADMIN_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var payEntity = PaymentMethod.builder()
                .value(reqDto.getValue())
                .isVisible(reqDto.isVisible())
                .admin(admin)
                .build();

        paymentMethodRepo.save(payEntity);
    }

    @Override
    public void changeVisibility(UUID id, boolean isVisible) {
        var adminId = authUtil.getAuthenticatedAdmin().getId();

        var admin = adminProfileRepo.findById(adminId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.ADMIN_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        var entity = paymentMethodRepo.findById(id)
                .orElseThrow(() -> new AppException(
                        "Payment method not found with ID: " + id,
                        HttpStatus.NOT_FOUND
                ));

        if (entity.isVisible() != isVisible) {
            entity.setVisible(isVisible);
            entity.setAdmin(admin);
            paymentMethodRepo.save(entity);
        }
    }

    private static List<PayMethodResDto> getPayMethodResDtos(List<PaymentMethod> pays) {
        if (pays.isEmpty()) {
            return new ArrayList<>();
        }

        return pays.stream()
                .map(pay -> PayMethodResDto.builder()
                        .id(pay.getId())
                        .value(pay.getValue())
                        .isVisible(pay.isVisible())
                        .createdAt(pay.getCreatedAt())
                        .updatedAt(pay.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
