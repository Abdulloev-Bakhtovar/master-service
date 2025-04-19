package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocFileDto {

    MultipartFile profilePhoto;
    MultipartFile passportMainPhoto;
    MultipartFile passportRegistrationPhoto;
    MultipartFile snilsPhoto;
    MultipartFile innPhoto;
}
