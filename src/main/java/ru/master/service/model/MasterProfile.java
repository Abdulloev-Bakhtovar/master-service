package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.constant.Education;
import ru.master.service.constant.MaritalStatus;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "master_subservices",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
    List<Subservice> subservices = new ArrayList<>();
}
