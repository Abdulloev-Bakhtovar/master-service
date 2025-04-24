package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.ServiceRequest;

import java.util.UUID;

public interface ServiceRequestRepo extends JpaRepository<ServiceRequest, UUID> {
}
