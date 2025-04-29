package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.constants.ServiceType;
import ru.master.service.model.dto.CityDto;
import ru.master.service.model.dto.inner.ServiceCategoryForOrderDto;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClientOrderDto {

    ServiceType serviceType;
    ServiceCategoryForOrderDto serviceCategoryDto;
    String firstName;
    String lastName;
    String address;
    String phoneNumber;
    String comment;
    BigDecimal price;
    Instant preferredDateTime;
    boolean urgent;
    boolean agreeToTerms;
    CityDto cityDto;
}
