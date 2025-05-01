package ru.master.service.mapper;

import ru.master.service.auth.model.dto.response.EnumDto;
import ru.master.service.constant.DisplayableEnum;

public interface EnumMapper {

    <T extends Enum<T> & DisplayableEnum> EnumDto toDto(T enumValue);
}
