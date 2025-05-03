package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.model.dto.UserAgreementDto;

import java.util.UUID;

/**
 * Create profile for client
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClientProfileReqDto {

    String firstName;
    String lastName;
    String address;
    UUID cityId;
    UserAgreementDto userAgreementDto;
}
