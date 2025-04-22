package ru.master.service.mapper;

import ru.master.service.auth.model.User;
import ru.master.service.model.UserAgreement;
import ru.master.service.model.dto.UserAgreementDto;

public interface UserAgreementMapper {

    UserAgreement toDto(UserAgreementDto dto, User user);

    UserAgreementDto toDto(UserAgreement entity);
}
