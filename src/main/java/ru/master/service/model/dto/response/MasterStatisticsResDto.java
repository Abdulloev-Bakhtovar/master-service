package ru.master.service.model.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterStatisticsResDto {

    int balance;
    int availableOrders;

    int receivedLastMonth;
    int closedLastMonth;

    int receivedTotal;
    int closedTotal;
}
