package ru.master.service.service;

import ru.master.service.auth.model.dto.response.EnumDto;
import ru.master.service.constant.DisplayableEnum;

import java.util.List;

public interface EnumService {

    <E extends Enum<E> & DisplayableEnum> List<EnumDto> getAllValues(Class<E> enumClass);
}