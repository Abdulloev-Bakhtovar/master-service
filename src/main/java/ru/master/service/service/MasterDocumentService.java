package ru.master.service.service;

import ru.master.service.model.dto.request.MasterDocumentReqDto;
import ru.master.service.model.dto.response.MasterDocumentResDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface MasterDocumentService {

    List<MasterDocumentResDto> getAll(UUID masterId);

    void add(MasterDocumentReqDto reqDto) throws IOException;
}
