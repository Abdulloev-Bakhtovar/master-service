package ru.master.service.service;

import ru.master.service.constants.DisplayableEnum;
import ru.master.service.model.dto.EnumDto;

import java.util.List;

public interface EnumService {

    <E extends Enum<E> & DisplayableEnum> List<EnumDto> getAllValues(Class<E> enumClass);
}