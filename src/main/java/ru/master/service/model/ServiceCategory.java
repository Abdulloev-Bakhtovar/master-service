package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategory extends BaseCategory {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "service_subcategory_relations",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
    Set<SubServiceCategory> subServices = new HashSet<>();
}
