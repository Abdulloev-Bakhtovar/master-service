/*
package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategory extends BaseEntity {

    String name;

    @ManyToMany
    @JoinTable(
            name = "service_subservice_relations",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
    Set<SubServiceCategory> subServices = new HashSet<>();
}*/
