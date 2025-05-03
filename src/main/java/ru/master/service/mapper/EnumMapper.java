package ru.master.service.mapper;

import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.constant.DisplayableEnum;

public interface EnumMapper {

    <T extends Enum<T> & DisplayableEnum> EnumResDto toDto(T enumValue);
}
