package ru.master.service.model.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.BaseDto;
import ru.master.service.model.dto.EnumDto;
import ru.master.service.model.dto.MasterInfoDto;
import ru.master.service.model.dto.ServiceCategoryDto;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientOrderInfoDto extends BaseDto {

    ServiceCategoryDto serviceCategory;
    EnumDto serviceRequestStatus;
    EnumDto serviceType;
    String address;
    String phoneNumber;
    String comment;
    Instant preferredDateTime;
    boolean urgent;
    MasterInfoDto masterInfoDto;
}
