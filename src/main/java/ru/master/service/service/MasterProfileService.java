package ru.master.service.service;

import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.request.CreateMasterProfileDto;

public interface MasterProfileService {

    void create(CreateMasterProfileDto reqDto) throws Exception;

    void updateMasterAverageRating(MasterProfile master, float newRating);
}
