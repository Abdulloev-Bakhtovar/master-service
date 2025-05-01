package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.MasterProfile;

import java.util.Optional;
import java.util.UUID;

public interface MasterProfileRepo extends JpaRepository<MasterProfile, UUID> {

   Optional<MasterProfile> findByUserId(UUID userId);

   boolean existsByUserId(UUID userId);
}
