package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.model.dto.BaseCategoryDto;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListServiceCategoryDto extends BaseCategoryDto {
}