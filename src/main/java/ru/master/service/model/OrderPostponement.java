package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;

import java.time.Instant;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_postponement")
@ToString(exclude = {"order", "master"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderPostponement extends TimestampedEntity {

    Instant newAppointmentDate;
    String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    MasterProfile master;
}