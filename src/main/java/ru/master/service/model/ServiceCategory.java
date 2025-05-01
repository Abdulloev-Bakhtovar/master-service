package ru.master.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategory extends BaseCategory {

    @OneToMany(mappedBy = "serviceCategory")
    private List<SubServiceCategory> subServices;
}
