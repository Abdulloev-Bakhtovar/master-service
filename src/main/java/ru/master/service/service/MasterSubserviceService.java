package ru.master.service.service;

import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.MasterSubserviceDto;

import java.util.List;

public interface MasterSubserviceService {

    void create(List<MasterSubserviceDto> reqDtos, MasterProfile masterProfile);
}
