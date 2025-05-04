package ru.master.service.service;

import ru.master.service.auth.model.User;
import ru.master.service.model.dto.request.RejectMasterRequestReqDto;
import ru.master.service.model.dto.response.MasterRequestResDto;
import ru.master.service.model.dto.response.NewMasterRequestResDto;

import java.util.List;
import java.util.UUID;

public interface MasterRequestService {

    void create(User user);

    List<NewMasterRequestResDto> getAll();

    void approve(UUID requestId);

    void reject(UUID requestId, RejectMasterRequestReqDto reqDto);

    MasterRequestResDto getById(UUID requestId);
}
