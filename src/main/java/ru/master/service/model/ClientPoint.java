package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.constant.ClientPointType;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_points")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientPoint extends TimestampedEntity {

    @ManyToOne
    @JoinColumn(name = "client_profile_id")
    ClientProfile clientProfile;

    int points;

    @Enumerated(EnumType.STRING)
    ClientPointType type; // REFERRAL_REGISTRATION, REFERRAL_FIRST_ORDER
}