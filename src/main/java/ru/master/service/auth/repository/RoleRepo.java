package ru.master.service.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.master.service.auth.model.Role;
import ru.master.service.constants.RoleName;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(RoleName roleName);
}
