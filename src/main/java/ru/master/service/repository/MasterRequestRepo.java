package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.constants.VerificationStatus;
import ru.master.service.model.MasterRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MasterRequestRepo extends JpaRepository<MasterRequest, UUID> {

    boolean existsByUserId(UUID id);

    Optional<MasterRequest> findByUserId(UUID id);

    List<MasterRequest> findByUser_VerificationStatus(VerificationStatus verificationStatus);
}
