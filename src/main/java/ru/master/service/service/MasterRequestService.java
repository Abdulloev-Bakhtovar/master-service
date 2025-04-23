package ru.master.service.service;

import ru.master.service.auth.model.User;
import ru.master.service.model.dto.MasterRequestDto;
import ru.master.service.model.dto.NewMasterRequestDto;

import java.util.List;
import java.util.UUID;

public interface MasterRequestService {

    List<NewMasterRequestDto> getAll();

    MasterRequestDto getById(UUID Id);

    void create(User user);

    void approve(MasterRequestDto dto);

    void reject(MasterRequestDto dto);
}
