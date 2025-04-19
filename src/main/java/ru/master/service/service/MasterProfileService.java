package ru.master.service.service;

import ru.master.service.model.dto.DocFileDto;
import ru.master.service.model.dto.MasterProfileDto;

import java.io.IOException;

public interface MasterProfileService {

    void create(MasterProfileDto dto);

    void addDocFile(DocFileDto docFileDto) throws IOException;
}
