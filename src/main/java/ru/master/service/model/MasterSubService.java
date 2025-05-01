package ru.master.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;

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
