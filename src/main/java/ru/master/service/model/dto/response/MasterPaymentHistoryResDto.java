package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.constant.PaymentHistoryType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterPaymentHistoryResDto extends TimestampedDto {

    BigDecimal amount;
    PaymentHistoryType type;
    UUID orderId;
}
