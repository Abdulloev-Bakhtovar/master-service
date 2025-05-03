package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.model.dto.BaseCategoryDto;
import ru.master.service.model.dto.SubserviceForServiceDto;

import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategoryWithSubserviceResDto extends BaseCategoryDto {

    List<SubserviceForServiceDto> subserviceDtos;
}
