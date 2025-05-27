package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.PaymentHistoryType;
import ru.master.service.exception.AppException;
import ru.master.service.mapper.MasterPaymentHistoryMapper;
import ru.master.service.model.MasterPaymentHistory;
import ru.master.service.model.Order;
import ru.master.service.model.Payment;
import ru.master.service.model.dto.response.MasterPaymentHistoryResDto;
import ru.master.service.repository.MasterPaymentHistoryRepo;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.service.MasterPaymentHistoryService;
import ru.master.service.util.AuthUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MasterPaymentHistoryServiceImpl implements MasterPaymentHistoryService {

    private static final BigDecimal SERVICE_COMMISSION = BigDecimal.valueOf(200); // Комиссия сервиса

    private final MasterPaymentHistoryRepo historyRepo;
    private final AuthUtil authUtil;
    private final MasterProfileRepo masterProfileRepo;
    private final MasterPaymentHistoryMapper historyMapper;

    @Override
    public List<MasterPaymentHistoryResDto> getCreditHistory() {
        UUID userId = authUtil.getAuthenticatedUser().getId();
        var master = masterProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        List<PaymentHistoryType> creditTypes = List.of(
                PaymentHistoryType.CREDIT_ORDER,
                PaymentHistoryType.CREDIT_TOP_UP
        );

        return historyRepo.findAllByMasterIdAndTypeIn(master.getId(), creditTypes)
                .stream()
                .map(historyMapper::toDto)
                .toList();
    }

    @Override
    public List<MasterPaymentHistoryResDto> getDebitHistory() {
        UUID userId = authUtil.getAuthenticatedUser().getId();
        var master = masterProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        List<PaymentHistoryType> debitTypes = List.of(
                PaymentHistoryType.DEBIT_COMMISSION,
                PaymentHistoryType.DEBIT_WITHDRAWAL
        );

        return historyRepo.findAllByMasterIdAndTypeIn(master.getId(), debitTypes)
                .stream()
                .map(historyMapper::toDto)
                .toList();
    }

    @Override
    public void createForOrder(Order completeOrder) {

        var master = completeOrder.getMasterProfile();
        var currentBalance = master.getBalance() != null ? master.getBalance() : BigDecimal.ZERO;

        var creditAmount = completeOrder.getPrice();
        var debitAmount = SERVICE_COMMISSION;

        // CREDIT
        var creditHistory = MasterPaymentHistory.builder()
                .type(PaymentHistoryType.CREDIT_ORDER)
                .amount(creditAmount)
                .master(master)
                .order(completeOrder)
                .build();
        historyRepo.save(creditHistory);

        // DEBIT
        var debitHistory = MasterPaymentHistory.builder()
                .type(PaymentHistoryType.DEBIT_COMMISSION)
                .amount(debitAmount)
                .master(master)
                .order(completeOrder)
                .build();
        historyRepo.save(debitHistory);

        // Update balance
        master.setBalance(currentBalance.add(creditAmount).subtract(debitAmount));

        masterProfileRepo.save(master);
    }

    @Override
    public void createForTopUp(Payment payEntity) {
        var master = payEntity.getMaster();
        var currentBalance = master.getBalance() != null ? master.getBalance() : BigDecimal.ZERO;

        var amount = payEntity.getAmount();
        master.setBalance(currentBalance.add(amount));

        // сохраняем историю
        var history = MasterPaymentHistory.builder()
                .type(PaymentHistoryType.CREDIT_TOP_UP)
                .amount(amount)
                .master(master)
                .build();

        historyRepo.save(history);
        masterProfileRepo.save(master);
    }
}
