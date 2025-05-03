package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.model.dto.ServiceCategoryForAvailableOrdersDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AvailableOrdersForMasterDto extends TimestampedDto {

    ServiceCategoryForAvailableOrdersDto serviceCategoryDto;
    EnumResDto clientOrderStatus;
    EnumResDto masterOrderStatus;
    EnumResDto serviceType;
}
