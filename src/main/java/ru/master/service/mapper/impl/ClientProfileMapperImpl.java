package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.mapper.ClientProfileMapper;
import ru.master.service.model.City;
import ru.master.service.model.ClientProfile;
import ru.master.service.model.dto.request.CreateClientProfileReqDto;

@Component
public class ClientProfileMapperImpl implements ClientProfileMapper {

    @Override
    public ClientProfile toClientProfileEntity(CreateClientProfileReqDto dto, User user, City city) {
        if (dto == null) return null;

        return ClientProfile.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .city(city)
                .address(dto.getAddress())
                .user(user)
                .build();
    }
}
