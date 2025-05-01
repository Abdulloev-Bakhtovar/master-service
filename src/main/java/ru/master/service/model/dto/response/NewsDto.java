package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.model.dto.CityDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDto extends TimestampedDto {

    String title;
    String content;
    CityDto cityDto;
    boolean isVisible;
    MultipartFile photo;
}
