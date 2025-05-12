package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.constant.MasterStatus;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterStatusUpdateDto {

    MasterStatus masterStatus;
}
