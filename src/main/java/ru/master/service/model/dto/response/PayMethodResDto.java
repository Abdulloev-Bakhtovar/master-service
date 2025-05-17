package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.constant.PayMethod;
import ru.master.service.model.dto.AdminForPayMethodResDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayMethodResDto extends TimestampedDto {

    PayMethod value;
    AdminForPayMethodResDto adminDto;
}
