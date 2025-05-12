package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;

@Getter
@Setter
@Entity
@ToString(exclude = "city")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class News extends TimestampedEntity {

    String title;
    String content;
    boolean isVisible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    City city;
}
