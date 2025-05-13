package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.User;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_profiles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientProfile extends BaseProfile {

    String address;

    UUID referralCode;
    int totalPoints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_by")
    User referredBy; // Кто пригласил этого пользователя

    @OneToOne(mappedBy = "clientProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    ClientPoint clientPoint;

    @OneToMany(mappedBy = "referrer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Referral> referrals;
}
