package ru.master.service.model;


import jakarta.persistence.*;
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
@Table(name = "master_feedbacks")
@ToString(exclude = {"order", "master"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterFeedback extends TimestampedEntity {

    String review;
    Float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    MasterProfile master;
}
