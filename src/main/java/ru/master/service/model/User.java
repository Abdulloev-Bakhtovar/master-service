package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.enums.Role;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends TimestampedEntity {

    String phoneNumber;
    boolean isVerified;

    @Enumerated(EnumType.STRING)
    private Role role;
}