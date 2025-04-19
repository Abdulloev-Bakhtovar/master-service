package ru.master.service.mapper;

import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;

public interface MasterSubServiceMapper {

    MasterSubService toEntity(MasterProfile masterProfile, ServiceCategory service, SubServiceCategory sub);
}
