package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.constants.DisplayableEnum;
import ru.master.service.mapper.EnumMapper;
import ru.master.service.model.dto.EnumDto;


@Component
public class EnumMapperImpl implements EnumMapper {

    public <T extends Enum<T> & DisplayableEnum> EnumDto toDto(T enumValue) {
        if (enumValue == null) {
            return null;
        }

        return EnumDto.builder()
                .name(enumValue.name())
                .displayName(enumValue.getDisplayName())
                .build();
    }
}
