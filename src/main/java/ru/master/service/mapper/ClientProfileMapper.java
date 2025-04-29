package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.City;
import ru.master.service.model.ClientProfile;
import ru.master.service.model.dto.request.CreateClientProfileDto;

public interface ClientProfileMapper {

    ClientProfile toClientProfileEntity(CreateClientProfileDto dto, User user, City city);
}
