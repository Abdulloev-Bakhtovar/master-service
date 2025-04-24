package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.ServiceCategory;

import java.util.List;
import java.util.UUID;

public interface ServiceCategoryRepo extends JpaRepository<ServiceCategory, UUID> {

    boolean existsByName(String name);

    List<ServiceCategory> findByNameContainingIgnoreCase(String name);
}
