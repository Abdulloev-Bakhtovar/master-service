package ru.master.service.admin.model.dto.responce;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminOrderSummaryResDto {

    int openCount;
    double openPercentage;

    int closedCount;
    double closedPercentage;

    BigDecimal totalRevenue;
}
