package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.constants.ClientOrderStatus;
import ru.master.service.constants.ServiceType;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientOrder extends TimestampedEntity {

    String firstName;
    String lastName;
    String address;
    String phoneNumber;
    String comment;
    Instant preferredDateTime;
    boolean urgent;
    boolean agreeToTerms;
    BigDecimal price;

    @Enumerated(EnumType.STRING)
    ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    ClientOrderStatus clientOrderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_profile_id")
    ClientProfile clientProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_category_id")
    ServiceCategory serviceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subservice_category_id")
    SubServiceCategory subServiceCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_profile_id")
    MasterProfile masterProfile;
}
