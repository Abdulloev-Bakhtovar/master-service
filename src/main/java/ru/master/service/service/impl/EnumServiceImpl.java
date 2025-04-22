package ru.master.service.service.impl;

import org.springframework.stereotype.Service;
import ru.master.service.constants.DisplayableEnum;
import ru.master.service.model.dto.EnumDto;
import ru.master.service.service.EnumService;

import java.util.Arrays;
import java.util.List;

@Service
public class EnumServiceImpl implements EnumService {

    @Override
    public <E extends Enum<E> & DisplayableEnum> List<EnumDto> getAllValues(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(e -> EnumDto.builder()
                        .name(e.name())
                        .displayName(e.getDisplayName())
                        .build())
                .toList();
    }
}
