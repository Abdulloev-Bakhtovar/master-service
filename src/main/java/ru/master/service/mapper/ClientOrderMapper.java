package ru.master.service.mapper;

import ru.master.service.constants.ClientOrderStatus;
import ru.master.service.model.*;
import ru.master.service.model.dto.ClientOrderDto;
import ru.master.service.model.dto.request.ClientOrderInfoDto;

public interface ClientOrderMapper {

    ClientOrder toEntity(ClientOrderDto dto,
                         City city,
                         ClientProfile clientProfile,
                         ServiceCategory serviceCategory,
                         SubServiceCategory subServiceCategory,
                         ClientOrderStatus clientOrderStatus);

    ClientOrderDto toDto(ClientOrder entity);

    ClientOrderInfoDto orderInfoDto(ClientOrder entity);

    void mapWithMaster(ClientOrder clientOrder, MasterProfile master);
}
