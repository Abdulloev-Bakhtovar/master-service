package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.constant.PayMethod;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayMethodReqDto {
    PayMethod payMethod;
}
