package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.constant.ClientOrderStatus;
import ru.master.service.constant.MasterOrderStatus;
import ru.master.service.constant.ServiceType;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends TimestampedEntity {

    String firstName;
    String lastName;
    String address;
    String phoneNumber;
    String comment;
    Instant preferredDateTime;
    boolean urgent;
    boolean agreeToTerms;
    BigDecimal price;
    String rejectionReason;

    @Enumerated(EnumType.STRING)
    ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    ClientOrderStatus clientOrderStatus;

    @Enumerated(EnumType.STRING)
    MasterOrderStatus masterOrderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_profile_id")
    ClientProfile clientProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subservice_id")
    Subservice subservice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_profile_id")
    MasterProfile masterProfile;
}
