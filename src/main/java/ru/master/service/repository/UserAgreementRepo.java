package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.UserAgreement;

import java.util.UUID;

public interface UserAgreementRepo extends JpaRepository<UserAgreement, UUID> {

    boolean existsByUserId(UUID id);
}
