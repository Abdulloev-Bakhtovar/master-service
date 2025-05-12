package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.auth.model.User;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"city", "user"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseProfile extends TimestampedEntity {

    String firstName;
    String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    City city;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
