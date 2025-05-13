package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.BaseEntity;
import ru.master.service.constant.CertificateStatus;

import java.time.Instant;

@Entity
@Table(name = "certificates")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certificate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_profile_id")
    ClientProfile client;

    @Enumerated(EnumType.STRING)
    CertificateStatus status; // ACTIVE, USED, EXPIRED

    Instant issuedAt;
    Instant expiresAt;
}
