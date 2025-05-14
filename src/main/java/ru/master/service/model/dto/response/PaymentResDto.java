package ru.master.service.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true) // Игнорирует неизвестные поля
public class PaymentResDto {

    String id;
    String status;
    Amount amount;
    Confirmation confirmation;
    boolean paid;
    boolean test;
    String description;
    String orderId;

    @Data
    public static class Amount {
        private String value;
        private String currency;
    }

    @Data
    public static class Confirmation {
        private String type;

        @JsonProperty("confirmation_url")
        private String confirmationUrl;
    }
}
