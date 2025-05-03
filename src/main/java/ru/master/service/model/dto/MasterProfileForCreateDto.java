package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.constant.Education;
import ru.master.service.constant.MaritalStatus;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterProfileForCreateDto extends TimestampedDto {

    String firstName;
    String lastName;
    UserAgreementDto userAgreementDto;
    CityDto cityDto;
    String email;
    String workExperience;
    boolean hasConviction;
    float averageRating;
    MaritalStatus maritalStatus;
    Education education;
    List<MasterSubserviceDto> masterSubserviceDtos;
}
