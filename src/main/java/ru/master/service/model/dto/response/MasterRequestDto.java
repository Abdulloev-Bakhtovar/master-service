package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.model.dto.MasterProfileForMasterRequestDto;
import ru.master.service.model.dto.UserForMasterRequestDto;

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
    UserForMasterRequestDto userDto;
    MasterProfileForMasterRequestDto masterProfileDto;
}
