package ru.master.service.service;

import ru.master.service.auth.model.dto.response.EnumResDto;
import ru.master.service.constant.DisplayableEnum;

import java.util.List;

public interface EnumService {

    <E extends Enum<E> & DisplayableEnum> List<EnumResDto> getAllValues(Class<E> enumClass);
}