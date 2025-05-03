package ru.master.service.auth.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnumResDto {

    String name;
    String displayName;
}