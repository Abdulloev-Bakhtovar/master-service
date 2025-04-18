package ru.master.service.service;

import ru.master.service.auth.model.User;
import ru.master.service.model.dto.UserAgreementDto;

public interface UserAgreementService {

    void create(UserAgreementDto dto, User user);
}
