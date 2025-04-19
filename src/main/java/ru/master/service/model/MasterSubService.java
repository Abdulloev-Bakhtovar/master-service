package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "master_subservices")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterSubService extends TimestampedEntity {

    @ManyToOne
    @JoinColumn(name = "master_id")
    MasterProfile masterProfile;

    @ManyToOne
    @JoinColumn(name = "service_id")
    ServiceCategory serviceCategory;

    @ManyToOne
    @JoinColumn(name = "subservice_id")
    SubServiceCategory subServiceCategory;
}
