package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.mapper.MasterSubServiceMapper;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.MasterSubService;
import ru.master.service.model.ServiceCategory;
import ru.master.service.model.SubServiceCategory;

@Component
public class MasterSubServiceMapperImpl implements MasterSubServiceMapper {

    @Override
    public MasterSubService toEntity(MasterProfile masterProfile, ServiceCategory service, SubServiceCategory sub) {
        if (sub == null || service == null || masterProfile == null) return null; // TODO

        return MasterSubService.builder()
                .masterProfile(masterProfile)
                .serviceCategory(service)
                .subServiceCategory(sub)
                .build();
    }
}
