package ru.master.service.service;

import ru.master.service.model.MasterProfile;
import ru.master.service.model.dto.request.CreateMasterProfileDto;

import java.io.IOException;

public interface MasterProfileService {

    void create(CreateMasterProfileDto reqDto) throws IOException;

    void updateMasterAverageRating(MasterProfile master, float newRating);
}
