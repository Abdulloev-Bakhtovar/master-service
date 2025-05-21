package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import ru.master.service.constant.MasterDocumentType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterDocumentReqDto {

    MasterDocumentType type;
    String description;
    MultipartFile file;
}
