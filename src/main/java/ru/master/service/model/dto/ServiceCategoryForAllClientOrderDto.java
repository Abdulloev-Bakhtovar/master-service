package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(exclude = "subserviceDto")
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategoryForAllClientOrderDto extends BaseCategoryDto {

    SubserviceForAllClientOrderDto subserviceDto;
}
