package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.City;

import java.util.UUID;

public interface CityRepo extends JpaRepository<City, UUID> {

    boolean existsByName(String name);
}
