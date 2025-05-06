package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.BaseDto;
import ru.master.service.model.dto.CityDto;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientInfoForCreateOrderResDto extends BaseDto {

    String firstName;
    String lastName;
    CityDto cityDto;
    String address;
    String phoneNumber;
}