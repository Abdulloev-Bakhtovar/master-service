package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.PaymentMethod;

import java.util.UUID;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, UUID> {
}
