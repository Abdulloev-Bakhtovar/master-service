package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.mapper.ClientProfileMapper;
import ru.master.service.model.City;
import ru.master.service.model.ClientProfile;
import ru.master.service.model.dto.CityDto;
import ru.master.service.model.dto.request.CreateClientProfileReqDto;
import ru.master.service.model.dto.response.ClientInfoForCreateOrderResDto;
import ru.master.service.util.CodeGeneratorUtil;

@Component
public class ClientProfileMapperImpl implements ClientProfileMapper {

    /**
     * Mapper for create client profile
     * @param dto
     * @param user
     * @param city
     * @return ClientProfile
     */
    @Override
    public ClientProfile toEntity(CreateClientProfileReqDto dto, User user, City city) {
        if (dto == null) return null;

        return ClientProfile.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .totalEarnedPoints(0)
                .referralCode(CodeGeneratorUtil.generateReferralCode())
                .city(city)
                .address(dto.getAddress())
                .user(user)
                .build();
    }

    /**
     * get client info for create order
     * @param clientProfile
     * @return ClientInfoForCreateOrderResDto
     */
    @Override
    public ClientInfoForCreateOrderResDto toClientInfoForCreateOrderResDto(ClientProfile clientProfile) {
        if (clientProfile == null) return null;

        var cityDto = CityDto.builder()
                .id(clientProfile.getCity().getId())
                .name(clientProfile.getCity().getName())
                .isVisible(clientProfile.getCity().isVisible())
                .build();

        return ClientInfoForCreateOrderResDto.builder()
                .id(clientProfile.getId())
                .firstName(clientProfile.getFirstName())
                .lastName(clientProfile.getLastName())
                .phoneNumber(clientProfile.getUser().getPhoneNumber())
                .address(clientProfile.getAddress())
                .cityDto(cityDto)
                .build();
    }
}
