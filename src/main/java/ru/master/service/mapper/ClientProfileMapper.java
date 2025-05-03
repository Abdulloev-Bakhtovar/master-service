package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.City;
import ru.master.service.model.ClientProfile;
import ru.master.service.model.dto.request.CreateClientProfileReqDto;

public interface ClientProfileMapper {

    ClientProfile toEntity(CreateClientProfileReqDto dto, User user, City city);
}
