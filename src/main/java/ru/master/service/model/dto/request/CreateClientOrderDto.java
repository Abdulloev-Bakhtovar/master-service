package ru.master.service.model.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.master.service.constant.ServiceType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateClientOrderDto {

    ServiceType serviceType;
    UUID serviceCategoryId;
    UUID subServiceCategoryId;
    String firstName;
    String lastName;
    UUID cityId;
    String address;
    String phoneNumber;
    String comment;
    Instant preferredDateTime;
    boolean urgent;
    boolean agreeToTerms;
    BigDecimal price;
}
