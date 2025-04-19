package ru.master.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "service_subcategories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubServiceCategory extends BaseCategory {
}
