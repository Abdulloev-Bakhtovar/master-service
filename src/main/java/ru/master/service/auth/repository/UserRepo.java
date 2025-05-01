package ru.master.service.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.auth.model.User;
import ru.master.service.constant.VerificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);

    List<User> findByVerificationStatus(VerificationStatus verificationStatus);
}
