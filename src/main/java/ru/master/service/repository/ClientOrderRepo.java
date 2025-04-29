package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.ClientOrder;

import java.util.UUID;

public interface ClientOrderRepo extends JpaRepository<ClientOrder, UUID> {
}
