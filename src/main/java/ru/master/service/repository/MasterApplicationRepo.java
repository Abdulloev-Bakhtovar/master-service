package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.MasterApplication;

import java.util.Optional;
import java.util.UUID;

public interface MasterApplicationRepo extends JpaRepository<MasterApplication, UUID> {

    boolean existsByUserId(UUID id);

    Optional<MasterApplication> findByUserId(UUID id);
}
