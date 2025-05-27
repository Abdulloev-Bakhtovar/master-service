package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.constant.PaymentHistoryType;
import ru.master.service.model.MasterPaymentHistory;

import java.util.List;
import java.util.UUID;

public interface MasterPaymentHistoryRepo extends JpaRepository<MasterPaymentHistory, UUID> {

    List<MasterPaymentHistory> findAllByMasterIdAndTypeIn(UUID id, List<PaymentHistoryType> creditTypes);
}
