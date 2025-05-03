package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.constant.DisplayableEnum;
import ru.master.service.mapper.EnumMapper;


@Component
public class EnumMapperImpl implements EnumMapper {

    public <T extends Enum<T> & DisplayableEnum> EnumResDto toDto(T enumValue) {
        if (enumValue == null) {
            return null;
        }

        return EnumResDto.builder()
                .name(enumValue.name())
                .displayName(enumValue.getDisplayName())
                .build();
    }
}
