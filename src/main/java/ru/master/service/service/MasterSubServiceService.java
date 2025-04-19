package ru.master.service.service;

import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.ServiceCategoryDto;

import java.util.List;

public interface MasterSubServiceService {

    void create(List<ServiceCategoryDto> serviceCategoryDtos, MasterProfile masterProfile);
}
