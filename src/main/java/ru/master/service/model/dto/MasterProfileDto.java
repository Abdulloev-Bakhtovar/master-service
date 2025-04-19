package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.constants.Education;
import ru.master.service.constants.MaritalStatus;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterProfileDto extends BaseProfileDto {

    String email;
    String workExperience;
    boolean hasConviction;
    MaritalStatus maritalStatus;
    Education education;
    List<ServiceCategoryDto> serviceCategoryDtos;
}
