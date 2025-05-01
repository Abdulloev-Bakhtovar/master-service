package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.auth.model.User;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_agreements")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAgreement extends TimestampedEntity {

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
