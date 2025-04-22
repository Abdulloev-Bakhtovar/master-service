package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnumDto {

    String name;
    String displayName;
}
