package ru.master.service.model.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterStatisticsResDto {

    BigDecimal balance;
    int availableOrders;

    int receivedLastMonth;
    int closedLastMonth;

    int receivedTotal;
    int closedTotal;
}
