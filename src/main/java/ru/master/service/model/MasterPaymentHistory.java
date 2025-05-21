package ru.master.service.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.TimestampedEntity;
import ru.master.service.constant.PaymentHistoryType;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "master_payment_history")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterPaymentHistory extends TimestampedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    MasterProfile master;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    Order order;

    @Enumerated(EnumType.STRING)
    PaymentHistoryType type;

    BigDecimal amount;
}
