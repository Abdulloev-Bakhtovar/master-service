package ru.master.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.master.service.auth.model.User;
import ru.master.service.mapper.UserAgreementMapper;
import ru.master.service.model.UserAgreement;
import ru.master.service.model.dto.UserAgreementDto;

@Component
public class UserAgreementMapperImpl implements UserAgreementMapper {

    @Override
    public UserAgreement toDto(UserAgreementDto dto, User user) {
        if (dto == null) return null;

        return UserAgreement.builder()
                .personalDataConsent(dto.isPersonalDataConsent())
                .notificationsAllowed(dto.isNotificationsAllowed())
                .locationAccessAllowed(dto.isLocationAccessAllowed())
                .serviceTermsAccepted(dto.getServiceTermsAccepted())
                .serviceRulesAccepted(dto.getServiceRulesAccepted())
                .user(user)
                .build();
    }

    @Override
    public UserAgreementDto toDto(UserAgreement entity) {
        if (entity == null) return null;

        return UserAgreementDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .personalDataConsent(entity.isPersonalDataConsent())
                .notificationsAllowed(entity.isNotificationsAllowed())
                .locationAccessAllowed(entity.isLocationAccessAllowed())
                .serviceTermsAccepted(entity.getServiceTermsAccepted())
                .serviceRulesAccepted(entity.getServiceRulesAccepted())
                .build();
    }
}
