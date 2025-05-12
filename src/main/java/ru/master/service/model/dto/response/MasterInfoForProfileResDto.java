package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterInfoForProfileResDto {

    String firstName;
    String lastName;
    BigDecimal balance;
    float averageRating;
    long ratingCount;
}
