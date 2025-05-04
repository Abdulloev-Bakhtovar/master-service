package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.OrderPostponement;

import java.util.UUID;

public interface OrderPostponementRepo extends JpaRepository<OrderPostponement, UUID> {
}
