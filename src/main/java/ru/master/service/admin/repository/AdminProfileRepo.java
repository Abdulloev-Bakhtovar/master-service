package ru.master.service.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.admin.model.AdminProfile;

import java.util.Optional;
import java.util.UUID;

public interface AdminProfileRepo extends JpaRepository<AdminProfile, UUID> {

    Optional<AdminProfile> findByEmail(String email);
}
