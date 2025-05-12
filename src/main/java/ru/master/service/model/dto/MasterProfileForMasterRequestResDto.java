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
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"subserviceDtos", "cityDto"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterProfileForMasterRequestResDto extends TimestampedDto {

    String firstName;
    String lastName;
    CityDto cityDto;
    String email;
    String workExperience;
    boolean hasConviction;
    float averageRating;
    long ratingCount;
    MaritalStatus maritalStatus;
    Education education;
    List<MasterSubserviceDto> subserviceDtos;
}
