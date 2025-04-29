package ru.master.service.model.dto.responce;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.model.dto.BaseCategoryDto;
import ru.master.service.model.dto.SubServiceCategoryDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategoryDto extends BaseCategoryDto {

    List<SubServiceCategoryDto> subServiceCategoryDtos;
}
