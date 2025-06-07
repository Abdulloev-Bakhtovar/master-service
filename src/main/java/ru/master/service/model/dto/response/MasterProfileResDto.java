package ru.master.service.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.model.dto.MasterProfileForMasterRequestResDto;
import ru.master.service.model.dto.UserDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"masterInfoDto", "userDto"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MasterProfileResDto {

    UserDto userDto;
    MasterProfileForMasterRequestResDto masterInfoDto;
    int completedOrdersThisMonth;
    int totalCompletedOrders;
}