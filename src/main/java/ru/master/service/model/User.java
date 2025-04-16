package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.enums.Education;
import ru.master.service.enums.MaritalStatus;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends TimestampedEntity {

    String phoneNumber;
    String firstName;
    String lastName;
    String city;
    String email;
    String profileImagePath;
    Integer workExperience;
    Boolean hasCriminalRecord;

    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    Education education;
}