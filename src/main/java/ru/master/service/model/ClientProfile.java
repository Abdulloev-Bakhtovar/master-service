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
@AllArgsConstructor
@Table(name = "client_profiles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientProfile extends BaseProfile {

    String address;
}
