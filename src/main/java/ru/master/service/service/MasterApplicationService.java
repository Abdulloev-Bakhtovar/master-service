package ru.master.service.service;

import ru.master.service.auth.model.User;
import ru.master.service.model.dto.NewMasterRequestDto;
import ru.master.service.model.dto.MasterApplicationDto;

import java.util.List;

public interface MasterApplicationService {

    List<MasterApplicationDto> getAll1();

    List<NewMasterRequestDto> getAll();

    void create(User user);

    void approve(MasterApplicationDto dto);

    void reject(MasterApplicationDto dto);
}
