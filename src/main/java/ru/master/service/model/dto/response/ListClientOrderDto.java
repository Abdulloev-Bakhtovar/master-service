package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.model.dto.EnumDto;
import ru.master.service.model.dto.inner.ServiceCategoryForOrderDto;

/**
 * My orders page for client
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListClientOrderDto extends TimestampedDto {

    ServiceCategoryForOrderDto serviceCategoryDto;
    EnumDto clientOrderStatus;
    EnumDto serviceType;
}
