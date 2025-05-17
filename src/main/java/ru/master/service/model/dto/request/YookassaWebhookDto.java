package ru.master.service.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true) // Игнорирует неизвестные поля
public class YookassaWebhookDto {

    private String type; // всегда "notification"
    private String event; // например, "payment.succeeded"
    private PaymentObject object;

    @Data
    public static class PaymentObject {
        private String id;
        private String status;
        private boolean paid;
        private Amount amount;

        @Data
        public static class Amount {
            private String value;
            private String currency;
        }
    }
}
