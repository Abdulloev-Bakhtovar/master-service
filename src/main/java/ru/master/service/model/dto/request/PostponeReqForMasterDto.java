package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostponeReqForMasterDto {

    Instant newAppointmentDate;
    String reason;
}
