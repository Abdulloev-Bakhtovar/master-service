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

    boolean allowNotifications;
    boolean allowLocation;
    boolean personalDataConsent;
    Boolean serviceTermsConsent; // может быть null для клиента

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
