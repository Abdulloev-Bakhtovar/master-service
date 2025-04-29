package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.constants.Education;
import ru.master.service.constants.MaritalStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMasterProfileDto {

    String firstName;
    String lastName;
    String email;
    String workExperience;
    boolean hasConviction;
    MaritalStatus maritalStatus;
    Education education;
    UUID cityId;
    boolean personalDataConsent;
    boolean notificationsAllowed;
    boolean locationAccessAllowed;
    boolean serviceTermsAccepted;
    boolean serviceRulesAccepted;
    List<UUID> serviceCategoryIds;
    List<UUID> subServiceCategoryIds;
    MultipartFile profilePhoto;
    MultipartFile passportMainPhoto;
    MultipartFile passportRegistrationPhoto;
    MultipartFile snilsPhoto;
    MultipartFile innPhoto;
}
