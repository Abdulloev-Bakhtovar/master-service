package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAgreementDto extends TimestampedDto {

    /**
     * Согласие на обработку персональных данных.
     * Обязательно для всех пользователей (мастеров и клиентов).
     */
    boolean personalDataConsent;

    /**
     * Разрешение на получение push-уведомлений от системы.
     */
    boolean notificationsAllowed;

    /**
     * Разрешение на доступ к геолокации устройства.
     */
    boolean locationAccessAllowed;

    /**
     * Согласие с условиями использования приложения.
     * (доступно только для мастеров, для клиентов может быть null)
     */
    Boolean serviceTermsAccepted;

    /**
     * Согласие с правилами оказания услуг.
     * (доступно только для мастеров, для клиентов может быть null)
     */
    Boolean serviceRulesAccepted;
}
