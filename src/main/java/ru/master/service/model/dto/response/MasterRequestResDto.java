package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.model.dto.MasterProfileForMasterRequestResDto;
import ru.master.service.model.dto.UserDto;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterRequestResDto extends TimestampedDto {

    String rejectionReason;
    UUID reviewedByAdminId;
    UserDto userDto;
    MasterProfileForMasterRequestResDto masterInfoDto;
}
