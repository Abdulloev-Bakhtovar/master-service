package ru.master.service.model.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.BaseDto;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterInfoForClientOrderDetailDto extends BaseDto {

    String firstName;
    String lastName;
    String phoneNumber;
    float averageRating;
}
