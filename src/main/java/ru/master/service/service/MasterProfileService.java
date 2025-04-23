package ru.master.service.service;

import ru.master.service.model.dto.MasterProfileCreateDto;

import java.io.IOException;

public interface MasterProfileService {

    void create(MasterProfileCreateDto dto) throws IOException;
}
