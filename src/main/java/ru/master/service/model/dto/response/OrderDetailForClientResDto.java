package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.model.dto.MasterFeedbackForClientOrderDetailDto;
import ru.master.service.model.dto.MasterInfoForClientOrderDetailDto;
import ru.master.service.model.dto.ServiceCategoryForClientOrderDetailDto;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailForClientResDto extends TimestampedDto {

    ServiceCategoryForClientOrderDetailDto serviceCategoryDto;
    EnumResDto clientOrderStatus;
    EnumResDto serviceType;
    String address;
    String phoneNumber;
    Instant preferredDateTime;
    boolean urgent;
    String comment;
    MasterInfoForClientOrderDetailDto masterInfoDto;
    MasterFeedbackForClientOrderDetailDto masterFeedbackDto;
}
