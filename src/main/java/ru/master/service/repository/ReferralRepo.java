package ru.master.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.master.service.model.Referral;

import java.util.UUID;

public interface ReferralRepo extends JpaRepository<Referral, UUID> {
}
