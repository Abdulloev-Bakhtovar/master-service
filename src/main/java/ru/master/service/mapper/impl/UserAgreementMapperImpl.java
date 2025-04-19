package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.mapper.UserAgreementMapper;
import ru.master.service.model.UserAgreement;
import ru.master.service.model.dto.MasterProfileDto;
import ru.master.service.model.dto.UserAgreementDto;

@Component
public class UserAgreementMapperImpl implements UserAgreementMapper {

    @Override
    public UserAgreement toDto(UserAgreementDto dto, User user) {
        if (dto == null) return null;

        return UserAgreement.builder()
                .allowNotifications(dto.isAllowNotifications())
                .allowLocation(dto.isAllowLocation())
                .personalDataConsent(dto.isPersonalDataConsent())
                .serviceTermsConsent(dto.getServiceTermsConsent()) //TODO
                .user(user)
                .build();
    }

    @Override
    public UserAgreementDto toDto(MasterProfileDto dto) {
        if (dto == null) return null;

        return UserAgreementDto.builder()
                .allowLocation(dto.isAllowLocation())
                .allowNotifications(dto.isAllowNotifications())
                .serviceTermsConsent(dto.isServiceTermsConsent())
                .personalDataConsent(dto.isPersonalDataConsent())
                .build();
    }
}
