package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.admin.model.AdminProfile;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.constant.PayMethod;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_method")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethod extends TimestampedEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    AdminProfile admin;

    @Enumerated(EnumType.STRING)
    PayMethod value;

    boolean isVisible;
}
