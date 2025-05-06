package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.constant.Education;
import ru.master.service.constant.MaritalStatus;
import ru.master.service.constant.MasterStatus;

import java.math.BigDecimal;
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
    String workExperience;
    boolean hasConviction;
    float averageRating;
    long ratingCount;
    BigDecimal balance;

    @Enumerated(EnumType.STRING)
    Education education;

    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    MasterStatus masterStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "master_subservices",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "subservice_id")
    )
    List<Subservice> subservices = new ArrayList<>();
}
