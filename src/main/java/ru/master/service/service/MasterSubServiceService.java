package ru.master.service.service;

import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.ServiceCategoryForMasterProfileDto;

import java.util.List;

public interface MasterSubServiceService {

    void create(List<ServiceCategoryForMasterProfileDto> reqDtos, MasterProfile masterProfile);
}
