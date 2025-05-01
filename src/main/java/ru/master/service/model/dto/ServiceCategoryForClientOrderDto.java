package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategoryForClientOrderDto extends BaseCategoryDto {

    SubServiceForServiceDto subServiceCategoryDto;
}
