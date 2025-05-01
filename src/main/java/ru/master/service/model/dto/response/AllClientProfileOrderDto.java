package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.auth.model.dto.response.EnumDto;
import ru.master.service.model.dto.ServiceCategoryForClientOrderDto;

/**
 * My orders page for client
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllClientProfileOrderDto extends TimestampedDto {

    ServiceCategoryForClientOrderDto serviceCategoryDto;
    EnumDto clientOrderStatus;
    EnumDto masterOrderStatus;
    EnumDto serviceType;
}
