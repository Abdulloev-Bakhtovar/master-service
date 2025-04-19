package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.SubServiceCategory;

import java.util.UUID;

public interface SubServiceCategoryRepo extends JpaRepository<SubServiceCategory, UUID> {

    boolean existsByName(String name);
}
