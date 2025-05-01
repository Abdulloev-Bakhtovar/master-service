package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.auth.model.dto.response.EnumDto;
import ru.master.service.model.dto.ServiceCategoryForClientOrderDto;

/**
 * My orders page for master
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllMasterProfileOrderDto extends TimestampedDto {

    ServiceCategoryForClientOrderDto serviceCategoryDto;
    String address;
    EnumDto clientOrderStatus;
    EnumDto masterOrderStatus;
    EnumDto serviceType;
}
