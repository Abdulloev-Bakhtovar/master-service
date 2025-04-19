package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.City;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.MasterProfileDto;

public interface MasterProfileMapper {

    MasterProfile toEntity(MasterProfileDto dto, User user, City city);
}
