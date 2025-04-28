package ru.master.service.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.master.service.auth.model.dto.TimestampedDto;
import ru.master.service.constants.ServiceRequestStatus;
import ru.master.service.constants.ServiceType;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceRequestDto extends TimestampedDto {

    String firstName;
    String lastName;
    String address;
    String phoneNumber;
    String comment;
    Instant preferredDateTime;
    boolean urgent;
    boolean agreeToTerms;
    ServiceType serviceType;
    CityDto cityDto;
    ClientProfileDto clientProfileDto;
    ServiceCategoryDto serviceCategoryDto;
    BigDecimal price;
    ServiceRequestStatus serviceRequestStatus;
}
