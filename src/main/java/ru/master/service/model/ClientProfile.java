package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    String referralCode;
    int totalEarnedPoints;

    @OneToOne(mappedBy = "clientProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    ClientPoint clientPoint;

    @OneToMany(mappedBy = "referrer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Referral> referrals;
}
