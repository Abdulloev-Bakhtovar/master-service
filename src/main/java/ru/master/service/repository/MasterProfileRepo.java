package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.MasterProfile;

import java.util.UUID;

public interface MasterProfileRepo extends JpaRepository<MasterProfile, UUID> {

    boolean existsByUserId(UUID id);
}
