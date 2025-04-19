package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.constants.Education;
import ru.master.service.constants.MaritalStatus;

import java.util.UUID;

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
    boolean allowNotifications;
    boolean allowLocation;
    boolean personalDataConsent;
    boolean serviceTermsConsent;
    UUID cityId;
    MultipartFile profilePhoto;
    MultipartFile passportMainPhoto;
    MultipartFile passportRegistrationPhoto;
    MultipartFile snilsPhoto;
    MultipartFile innPhoto;
}
