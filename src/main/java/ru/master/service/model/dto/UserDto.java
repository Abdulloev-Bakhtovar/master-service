package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.constant.Role;
import ru.master.service.constant.VerificationStatus;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto extends TimestampedDto {

    String phoneNumber;
    Role role;
    VerificationStatus verificationStatus;
}
