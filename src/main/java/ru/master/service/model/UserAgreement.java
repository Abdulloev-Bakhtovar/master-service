//юридические согласия и разрешения users
package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
