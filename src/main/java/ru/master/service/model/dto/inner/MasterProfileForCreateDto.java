package ru.master.service.model.dto.inner;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.constants.Education;
import ru.master.service.constants.MaritalStatus;
import ru.master.service.model.dto.BaseProfileDto;
import ru.master.service.model.dto.responce.ServiceCategoryDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterProfileForCreateDto extends BaseProfileDto {

    String email;
    String workExperience;
    boolean hasConviction;
    float averageRating;
    MaritalStatus maritalStatus;
    Education education;
    List<ServiceCategoryDto> serviceCategoryDtos;
    MultipartFile profilePhoto;
    MultipartFile passportMainPhoto;
    MultipartFile passportRegistrationPhoto;
    MultipartFile snilsPhoto;
    MultipartFile innPhoto;
}
