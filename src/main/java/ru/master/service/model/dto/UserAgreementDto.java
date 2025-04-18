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
     * Разрешение на получение push-уведомлений от системы
     */
    boolean allowNotifications;

    /**
     * Разрешение на использование геолокации устройства
     */
    boolean allowLocation;

    /**
     * Флаг согласия на обработку персональных данных
     * (обязательное для заполнения)
     */
    boolean personalDataConsent;

    /**
     * Флаг принятия условий обслуживания
     * Всегда null для клиентов, предназначен для мастера
     */
    Boolean serviceTermsConsent;
}
