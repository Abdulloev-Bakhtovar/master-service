package ru.master.service.mapper;

import ru.master.service.constants.DisplayableEnum;
import ru.master.service.model.dto.EnumDto;

public interface EnumMapper {

    <T extends Enum<T> & DisplayableEnum> EnumDto toDto(T enumValue);
}
