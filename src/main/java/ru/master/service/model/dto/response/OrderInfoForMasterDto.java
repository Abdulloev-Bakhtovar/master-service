package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.model.dto.ClientInfoForMasterDto;
import ru.master.service.model.dto.MasterFeedbackForClientOrderDto;
import ru.master.service.model.dto.ServiceCategoryForClientOrderDto;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderInfoForMasterDto extends TimestampedDto {

    ServiceCategoryForClientOrderDto serviceCategoryDto;
    EnumResDto clientOrderStatus;
    EnumResDto masterOrderStatus;
    EnumResDto serviceType;
    String address;
    String phoneNumber;
    Instant preferredDateTime;
    boolean urgent;
    String comment;
    ClientInfoForMasterDto clientInfoDto;
    MasterFeedbackForClientOrderDto masterFeedbackDto;
}