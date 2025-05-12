package ru.master.service.model;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;

@Getter
@Setter
@ToString
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseCategory extends TimestampedEntity {

    String name;
}
