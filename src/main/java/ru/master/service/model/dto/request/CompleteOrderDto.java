package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.auth.model.dto.BaseDto;
import ru.master.service.model.dto.MasterFeedbackForClientOrderDto;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteOrderDto extends BaseDto {

    MasterFeedbackForClientOrderDto masterFeedback;
}
