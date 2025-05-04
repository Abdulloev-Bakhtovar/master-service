package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.model.dto.ServiceCategoryForAllClientOrderDto;

/** My orders page for client */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllClientOrderResDto extends TimestampedDto {

    ServiceCategoryForAllClientOrderDto serviceCategoryDto;
    EnumResDto clientOrderStatus;
    EnumResDto masterOrderStatus;
    EnumResDto serviceType;
}
