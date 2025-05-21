package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.PaymentMethod;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, UUID> {

    List<PaymentMethod> findByIsVisibleTrue();
}
