package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.City;
import ru.master.service.model.ClientProfile;
import ru.master.service.model.dto.ClientProfileDto;

public interface ClientProfileMapper {

    ClientProfile toEntity(ClientProfileDto dto, User user, City city);
}
