package ru.master.service.model.dto.responce;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.constants.ClientOrderStatus;
import ru.master.service.constants.ServiceType;
import ru.master.service.model.dto.EnumDto;
import ru.master.service.model.dto.inner.MasterInfoForOrderDto;
import ru.master.service.model.dto.inner.ServiceCategoryForOrderDto;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderInfoDto extends TimestampedDto {

    ServiceCategoryForOrderDto serviceCategoryDto;
    EnumDto clientOrderStatus;
    EnumDto serviceType;
    String address;
    String phoneNumber;
    Instant preferredDateTime;
    boolean urgent;
    String comment;
    MasterInfoForOrderDto masterDto;
    Float clientRating;
    String clientFeedback;
}
