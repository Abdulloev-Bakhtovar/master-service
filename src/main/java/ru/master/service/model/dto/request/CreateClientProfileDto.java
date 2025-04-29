package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.model.dto.BaseProfileDto;
import ru.master.service.model.dto.CityDto;
import ru.master.service.model.dto.UserAgreementDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClientProfileDto {

    String firstName;
    String lastName;
    String address;
    CityDto cityDto;
    UserAgreementDto userAgreementDto;
}
