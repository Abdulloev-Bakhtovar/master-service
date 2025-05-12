package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString(exclude = "subserviceDto")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategoryForMasterCompletedOrdersDto extends BaseCategoryDto {

    SubserviceForMasterCompletedOrdersDto subserviceDto;
}
