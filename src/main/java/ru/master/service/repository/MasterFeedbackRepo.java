package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.MasterFeedback;

import java.util.Optional;
import java.util.UUID;

public interface MasterFeedbackRepo extends JpaRepository<MasterFeedback, UUID> {

    Optional<MasterFeedback> findByOrderId(UUID orderId);
}
