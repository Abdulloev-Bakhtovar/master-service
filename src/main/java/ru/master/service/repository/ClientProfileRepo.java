package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.ClientProfile;

import java.util.UUID;

public interface ClientProfileRepo extends JpaRepository<ClientProfile, UUID> {

    boolean existsByUserId(UUID userId);
}
