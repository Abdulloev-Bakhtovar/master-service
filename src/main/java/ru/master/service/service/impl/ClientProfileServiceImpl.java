package ru.master.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.model.dto.ClientProfileDto;
import ru.master.service.repository.ClientProfileRepo;
import ru.master.service.service.ClientProfileService;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientProfileServiceImpl implements ClientProfileService {

    private final ClientProfileRepo clientProfileRepo;

    @Override
    public void create(ClientProfileDto dto) {

    }
}
