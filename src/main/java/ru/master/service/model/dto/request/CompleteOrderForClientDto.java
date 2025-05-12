package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.model.dto.MasterFeedbackForCompleteOrderForClientDto;

@Getter
@Setter
@ToString(exclude = "masterFeedbackDto")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompleteOrderForClientDto {

    MasterFeedbackForCompleteOrderForClientDto masterFeedbackDto;
}
