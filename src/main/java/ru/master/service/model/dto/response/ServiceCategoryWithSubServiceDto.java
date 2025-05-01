package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.model.dto.BaseCategoryDto;
import ru.master.service.model.dto.SubServiceForServiceDto;

import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategoryWithSubServiceDto extends BaseCategoryDto {

    List<SubServiceForServiceDto> subServiceCategoryDtos;
}
