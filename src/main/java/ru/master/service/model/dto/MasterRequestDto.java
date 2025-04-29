package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.auth.model.dto.UserDto;
import ru.master.service.model.dto.inner.MasterProfileForCreateDto;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterRequestDto extends TimestampedDto {

    String rejectionReason;
    UUID reviewedByAdminId;
    UserDto userDto;
    MasterProfileForCreateDto masterProfileForCreateDto;
}
