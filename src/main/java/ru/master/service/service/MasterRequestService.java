package ru.master.service.service;

import ru.master.service.auth.model.User;
import ru.master.service.model.dto.request.MasterRequestRejectDto;
import ru.master.service.model.dto.response.MasterRequestDto;
import ru.master.service.model.dto.response.NewMasterRequestDto;

import java.util.List;
import java.util.UUID;

public interface MasterRequestService {

    List<NewMasterRequestDto> getAll();

    MasterRequestDto getById(UUID id);

    void create(User user);

    void reject(MasterRequestRejectDto rejectDto);

    void approve(UUID id);
}
