package ru.master.service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.master.service.config.YooKassaConfig;
import ru.master.service.constant.ErrorMessage;
import ru.master.service.constant.PaymentPurpose;
import ru.master.service.exception.AppException;
import ru.master.service.model.MasterProfile;
import ru.master.service.model.Order;
import ru.master.service.model.Payment;
import ru.master.service.model.dto.request.PaymentReqDto;
import ru.master.service.model.dto.request.YookassaWebhookDto;
import ru.master.service.model.dto.response.PaymentResDto;
import ru.master.service.repository.MasterProfileRepo;
import ru.master.service.repository.PaymentRepo;
import ru.master.service.service.MasterPaymentHistoryService;
import ru.master.service.service.PaymentService;
import ru.master.service.util.AuthUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final String YOOKASSA_API_URL = "https://api.yookassa.ru/v3/payments";
    private final PaymentRepo paymentRepo;
    private final YooKassaConfig yooKassaConfig;
    private final MasterPaymentHistoryService historyService;
    private final AuthUtil authUtil;
    private final MasterProfileRepo masterProfileRepo;

    /*public PaymentResDto createPayment(PaymentReqDto paymentRequest, Order order) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(YOOKASSA_API_URL);

        // Авторизация
        String auth = yooKassaConfig.getShopId() + ":" + yooKassaConfig.getSecretKey();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        // Заголовки
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader("Idempotence-Key", UUID.randomUUID().toString());

        // Тело запроса
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();

        // Основные параметры платежа
        requestBody.set("amount", objectMapper.createObjectNode()
                .put("value", paymentRequest.getAmount().toString())
                .put("currency", "RUB"));

        requestBody.put("capture", true);
        requestBody.put("description", paymentRequest.getDescription());

        requestBody.set("confirmation", objectMapper.createObjectNode()
                .put("type", "redirect")
                .put("return_url", yooKassaConfig.getReturnUrl()));

        // Тестовый режим
        if (yooKassaConfig.isTestMode()) {
            requestBody.put("test", true);
        }

        // Данные для чека
        ArrayNode items = objectMapper.createArrayNode();
        ObjectNode item = objectMapper.createObjectNode();
        item.put("description", paymentRequest.getDescription());
        item.put("quantity", 1);

        ObjectNode itemAmount = objectMapper.createObjectNode();
        itemAmount.put("value", paymentRequest.getAmount().toString());
        itemAmount.put("currency", "RUB");
        item.set("amount", itemAmount);

        item.put("vat_code", 1);
        items.add(item);

        // Метаданные
        ObjectNode metadata = objectMapper.createObjectNode();
        metadata.put("clientOrderId", String.valueOf(paymentRequest.getOrderId()));
        requestBody.set("metadata", metadata);

        try {
            httpPost.setEntity(
                    new StringEntity(objectMapper.writeValueAsString(requestBody), StandardCharsets.UTF_8
                    ));


            CloseableHttpResponse response = httpClient.execute(httpPost);
            String responseBody = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() == 200) {
                PaymentResDto paymentResDto = objectMapper.readValue(responseBody, PaymentResDto.class);

                // Дополнительно устанавливаем orderId из metadata
                JsonNode rootNode = objectMapper.readTree(responseBody);
                if (rootNode.has("metadata") && rootNode.get("metadata").has("clientOrderId")) {
                    paymentResDto.setClientOrderId(rootNode.get("metadata").get("clientOrderId").asText());
                }

                var payment = Payment.builder()
                        .status(paymentResDto.getStatus())
                        .externalId(paymentResDto.getId())
                        .paid(paymentResDto.isPaid())
                        .amount(paymentResDto.getAmount().getValue())
                        .description(paymentResDto.getDescription())
                        .confirmationUrl(paymentResDto.getConfirmation().getConfirmationUrl())
                        .order(order)
                        .build();

                paymentRepo.save(payment);

                return paymentResDto;
            } else {
                JsonNode errorNode = objectMapper.readTree(responseBody);
                String errorDesc = errorNode.path("description").asText();
                throw new AppException(errorDesc, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            System.out.println("Payment creation error" + e);
            throw new AppException("Payment processing error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println("Error closing HTTP client" + e);
            }
        }
    }*/

    public PaymentResDto createPayment(PaymentReqDto paymentRequest, Order order, MasterProfile master) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(YOOKASSA_API_URL);

        // Авторизация
        String auth = yooKassaConfig.getShopId() + ":" + yooKassaConfig.getSecretKey();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        // Заголовки
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader("Idempotence-Key", UUID.randomUUID().toString());

        // Тело запроса
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestBody = objectMapper.createObjectNode();

        // Основные параметры платежа
        requestBody.set("amount", objectMapper.createObjectNode()
                .put("value", paymentRequest.getAmount().toString())
                .put("currency", "RUB"));

        requestBody.put("capture", true);
        requestBody.put("description", paymentRequest.getDescription());

        requestBody.set("confirmation", objectMapper.createObjectNode()
                .put("type", "redirect")
                .put("return_url", yooKassaConfig.getReturnUrl()));

        if (yooKassaConfig.isTestMode()) {
            requestBody.put("test", true);
        }

        // Чек (только 1 товар, описание = описание платежа)
        ArrayNode items = objectMapper.createArrayNode();
        ObjectNode item = objectMapper.createObjectNode();
        item.put("description", paymentRequest.getDescription());
        item.put("quantity", 1);

        ObjectNode itemAmount = objectMapper.createObjectNode();
        itemAmount.put("value", paymentRequest.getAmount().toString());
        itemAmount.put("currency", "RUB");
        item.set("amount", itemAmount);

        item.put("vat_code", 1);
        items.add(item);

        // Метаданные
        ObjectNode metadata = objectMapper.createObjectNode();
        metadata.put("paymentPurpose", paymentRequest.getPaymentPurpose().name());
        if (order != null) {
            metadata.put("clientOrderId", String.valueOf(order.getId()));
        }
        if (master != null) {
            metadata.put("masterId", String.valueOf(master.getId()));
        }
        requestBody.set("metadata", metadata);

        try {
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(requestBody), StandardCharsets.UTF_8));

            CloseableHttpResponse response = httpClient.execute(httpPost);
            String responseBody = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() == 200) {
                PaymentResDto paymentResDto = objectMapper.readValue(responseBody, PaymentResDto.class);

                // Распарсить response заново, чтобы вытащить clientOrderId (если есть)
                JsonNode rootNode = objectMapper.readTree(responseBody);
                if (rootNode.has("metadata") && rootNode.get("metadata").has("clientOrderId")) {
                    paymentResDto.setClientOrderId(rootNode.get("metadata").get("clientOrderId").asText());
                }

                var payment = Payment.builder()
                        .status(paymentResDto.getStatus())
                        .externalId(paymentResDto.getId())
                        .paid(paymentResDto.isPaid())
                        .amount(paymentResDto.getAmount().getValue())
                        .description(paymentResDto.getDescription())
                        .confirmationUrl(paymentResDto.getConfirmation().getConfirmationUrl())
                        .paymentPurpose(paymentRequest.getPaymentPurpose())
                        .build();

                if (order != null) {
                    payment.setOrder(order);
                } else {
                    payment.setMaster(master);
                }

                paymentRepo.save(payment);
                return paymentResDto;

            } else {
                JsonNode errorNode = objectMapper.readTree(responseBody);
                String errorDesc = errorNode.path("description").asText();
                throw new AppException(errorDesc, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            System.out.println("Payment creation error: " + e);
            throw new AppException("Payment processing error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println("Error closing HTTP client: " + e);
            }
        }
    }

    public PaymentResDto checkAndUpdatePaymentStatus(String paymentId) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(YOOKASSA_API_URL + "/" + paymentId);

        String auth = yooKassaConfig.getShopId() + ":" + yooKassaConfig.getSecretKey();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth);

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String responseBody = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() == 200) {
                return new ObjectMapper().readValue(responseBody, PaymentResDto.class);
            } else {
                System.out.println("Payment status check failed: {}" + responseBody);
                throw new RuntimeException("Payment status check failed: " + responseBody);
            }
        } catch (Exception e) {
            System.out.println("Error checking payment status" + e);
            throw new RuntimeException("Error checking payment status", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                System.out.println("Error closing HTTP client" + e);
            }
        }
    }

    @Override
    public PaymentResDto createTopUpPayment(PaymentReqDto request) {

        UUID userId = authUtil.getAuthenticatedUser().getId();
        var master = masterProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new AppException(
                        ErrorMessage.MASTER_PROFILE_NOT_FOUND,
                        HttpStatus.NOT_FOUND
                ));

        String description = String.format("Пополнение баланса мастера ID: %s на сумму %.2f ₽",
                master.getId(), request.getAmount());

        PaymentReqDto paymentReqDto = PaymentReqDto.builder()
                .amount(request.getAmount())
                .description(description)
                .paymentPurpose(PaymentPurpose.MASTER_TOP_UP)
                .build();

        return this.createPayment(paymentReqDto, null, master);
    }

    @Override
    public void updatePaymentStatus(YookassaWebhookDto reqDto) {

        var payEntity = paymentRepo.findByExternalId(reqDto.getObject().getId())
                .orElseThrow(null);

        if (payEntity == null) {
            return;
        }

        payEntity.setStatus(reqDto.getObject().getStatus());
        if (reqDto.getObject().getStatus().equalsIgnoreCase("succeeded")) {
            payEntity.setPaid(true);// TODO добавит логирования
        }
        paymentRepo.save(payEntity);

        if (payEntity.getPaymentPurpose() == PaymentPurpose.MASTER_TOP_UP && payEntity.getMaster() != null) {

            historyService.createForTopUp(payEntity);
        } else if (payEntity.getPaymentPurpose() == PaymentPurpose.ORDER_PAYMENT && payEntity.getOrder() != null) {

            historyService.createForOrder(payEntity.getOrder());
        }
    }

    @Override
    public String computeHmacSHA256(String payload) throws NoSuchAlgorithmException, InvalidKeyException {
        String key = yooKassaConfig.getSecretKey();
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmacBytes);
    }

}