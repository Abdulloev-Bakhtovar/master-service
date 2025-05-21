package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.auth.model.dto.BaseDto;
import ru.master.service.constant.MasterDocumentType;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterDocumentReqDto extends BaseDto {

    MasterDocumentType type;
    String description;
    MultipartFile file;
}
