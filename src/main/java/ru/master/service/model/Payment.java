package ru.master.service.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.constant.PaymentPurpose;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends TimestampedEntity {

    /**ID от платежного провайдера (например, Yookassa)*/
    String externalId;
    String status;
    BigDecimal amount;
    String description;
    boolean paid;

    @Enumerated(EnumType.STRING)
    PaymentPurpose paymentPurpose;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    MasterProfile master;

    String confirmationUrl;
}

