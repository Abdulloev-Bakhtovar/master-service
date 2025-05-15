package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.model.dto.ClientInfoForMasterOrderDetailDto;
import ru.master.service.model.dto.MasterFeedbackForMasterOrderDetailDto;
import ru.master.service.model.dto.ServiceCategoryForMasterOrderDetailDto;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"clientInfoDto", "masterFeedbackDto", "serviceCategoryDto"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailForMasterResDto extends TimestampedDto {

    ServiceCategoryForMasterOrderDetailDto serviceCategoryDto;
    EnumResDto masterOrderStatus;
    EnumResDto serviceType;
    Instant preferredDateTime;
    boolean urgent;
    String comment;
    BigDecimal price;
    ClientInfoForMasterOrderDetailDto clientInfoDto;
    MasterFeedbackForMasterOrderDetailDto masterFeedbackDto;
}

