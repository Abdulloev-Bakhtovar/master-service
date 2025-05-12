package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.constant.Education;
import ru.master.service.constant.MaritalStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString(exclude = {
        "profilePhoto",
        "passportMainPhoto",
        "passportRegistrationPhoto",
        "snilsPhoto",
        "innPhoto"
})
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMasterProfileReqDto {

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
    List<UUID> subServiceIds;
    MultipartFile profilePhoto;
    MultipartFile passportMainPhoto;
    MultipartFile passportRegistrationPhoto;
    MultipartFile snilsPhoto;
    MultipartFile innPhoto;
}
