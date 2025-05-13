package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.ClientPoint;

import java.util.UUID;

public interface ClientPointRepo extends JpaRepository<ClientPoint, UUID> {
}
