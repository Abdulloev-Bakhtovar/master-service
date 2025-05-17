package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {

    Optional<Payment> findTopByOrderIdOrderByCreatedAtDesc(UUID orderId);

    Optional<Payment> findByExternalId(String paymentId);
}
