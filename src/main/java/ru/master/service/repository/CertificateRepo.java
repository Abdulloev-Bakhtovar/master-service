package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.Certificate;

import java.util.UUID;

public interface CertificateRepo extends JpaRepository<Certificate, UUID> {
}
