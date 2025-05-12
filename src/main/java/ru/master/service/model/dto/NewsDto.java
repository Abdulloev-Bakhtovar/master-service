package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.auth.model.dto.TimestampedDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"photo", "cityDto"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsDto extends TimestampedDto {

    String title;
    String content;
    CityDto cityDto;
    boolean isVisible;
    MultipartFile photo;
}
