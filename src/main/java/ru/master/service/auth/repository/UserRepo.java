package ru.master.service.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.auth.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
