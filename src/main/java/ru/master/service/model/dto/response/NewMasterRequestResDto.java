package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.BaseDto;
import ru.master.service.model.dto.CityDto;

@Getter
@Setter
@SuperBuilder
@ToString(exclude = ("cityDto"))
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewMasterRequestResDto extends BaseDto {

    CityDto cityDto;
    String firstName;
    String lastName;
    String phoneNumber;
    String workExperience;
}
