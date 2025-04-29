package ru.master.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.constants.Education;
import ru.master.service.constants.MaritalStatus;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "master_profiles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterProfile extends BaseProfile {

    String email;
    MaritalStatus maritalStatus;
    Education education;
    String workExperience;
    boolean hasConviction;
    float averageRating;
}
