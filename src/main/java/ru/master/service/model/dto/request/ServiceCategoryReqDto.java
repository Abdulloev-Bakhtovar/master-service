package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.model.dto.BaseCategoryDto;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceCategoryReqDto extends BaseCategoryDto {

    List<UUID> subServiceCategoryIds;
    MultipartFile photo;
}
