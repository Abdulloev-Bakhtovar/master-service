package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.Subservice;

import java.util.List;
import java.util.UUID;

public interface SubserviceRepo extends JpaRepository<Subservice, UUID> {

    boolean existsByName(String name);

    List<Subservice> findAllByServiceCategoryId(UUID serviceId);

    List<Subservice> findAllByIdIn(List<UUID> ids);
}
