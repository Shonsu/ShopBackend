package pl.shonsu.shop.order.service.payment.p24;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import pl.shonsu.shop.order.model.Order;
import pl.shonsu.shop.order.model.dto.NotoficationReceiveDto;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentMethodP24 {
    private final PaymentMethodP24Config config;

    public String initPayment(Order newOrder) {
        log.info("Inicjalizacja płatności");

        WebClient webClient = WebClient.builder()
                .filter(ExchangeFilterFunctions.basicAuthentication(config.getPosId().toString(),
                        config.isTestMode() ? config.getTestSecretKey() : config.getSecretKey()))
                .baseUrl(config.isTestMode() ? config.getTestApiUrl() : config.getApiUrl())
                .build();
                TransactionRegisterRequest trr = TransactionRegisterRequest.builder()
                        .merchantId(config.getMerchantId())
                        .posId(config.getPosId())
                        .sessionId(createSessionId(newOrder))
                        .amount(newOrder.getGrossValue().movePointRight(2).intValue())
                        .currency("PLN")
                        .description("Zamówienie id: " + newOrder.getId())
                        .email(newOrder.getEmail())
                        .client(newOrder.getFirstname() + " " + newOrder.getLastname())
                        .country("PL")
                        .language("pl")
                        .urlReturn(generateReturnUrl(newOrder.getOrderHash()))
                        .urlStatus(generateStatusUrl(newOrder.getOrderHash()))
                        .sign(createSign(newOrder))
                        .encoding("UTF-8")
                        .build();
                log.info("TransactionRegisterRequest: " + String.valueOf(trr));
        ResponseEntity<TransactionRegisterResponse> result = webClient.post().uri("/transaction/register")
                .bodyValue(trr)
                .retrieve()
                .onStatus(httpStatus -> {
                    log.error("Something went wrong " + httpStatus.name());
                    return httpStatus.is4xxClientError();
                }, clientResponse -> Mono.empty())
                .toEntity(TransactionRegisterResponse.class)
                .block();
                log.info("TransactionRegisterResponse: " + String.valueOf(result));
        if (result != null && result.getBody() != null && result.getBody().getData() != null) {
            return (config.isTestMode() ? config.getTestUrl() : config.getUrl()) + "/trnRequest/" +
                    result.getBody().getData().token();
        }
        return null;
    }

    private String generateStatusUrl(String orderHash) {
        String baseUrl = config.isTestMode() ? config.getTestUrlStatus() : config.getUrlStatus();
        return baseUrl + "/orders/notification/" + orderHash;
    }

    private String generateReturnUrl(String orderHash) {
        String baseUrl = config.isTestMode() ? config.getTestUrlReturn() : config.getUrlReturn();
        return baseUrl + "/order/notification/" + orderHash;
    }

    private String createSign(Order newOrder) {
        String json = "{\"sessionId\":\"" + createSessionId(newOrder) +
                "\",\"merchantId\":" + config.getMerchantId() +
                ",\"amount\":" + newOrder.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";
        log.info("createSign" + json);
        return DigestUtils.sha384Hex(json);
    }

    private String createSessionId(Order newOrder) {
        return "order_id_" + newOrder.getId().toString();
    }

    public String receiveNotification(Order order, NotoficationReceiveDto receiveDto) {
        log.info("receiveNotification receivedto:" + receiveDto.toString());
        validate(receiveDto, order);
        return verifiyPayment(receiveDto, order);
    }

    private String verifiyPayment(NotoficationReceiveDto receiveDto, Order order) {
        WebClient webClient = WebClient.builder()
                .filter(ExchangeFilterFunctions.basicAuthentication(config.getPosId().toString(),
                        config.isTestMode() ? config.getTestSecretKey() : config.getSecretKey()))
                .baseUrl(config.isTestMode() ? config.getTestApiUrl() : config.getApiUrl())
                .build();
        ResponseEntity<TrasactionVerifyResponse> result = webClient.put().uri("/transaction/verify")
                .bodyValue(TransactionVerifyRequest.builder()
                        .merchantId(config.getMerchantId())
                        .posId(config.getPosId())
                        .sessionId(createSessionId(order))
                        .amount(order.getGrossValue().movePointRight(2).intValue())
                        .currency("PLN")
                        .orderId(receiveDto.getOrderId())
                        .sign(createVerifySign(receiveDto, order))
                        .build()
                )
                .retrieve()
                .toEntity(TrasactionVerifyResponse.class)
                .block();
        log.info("Weryfikacja transakcji status: " + result.getBody().getData().status());
        return result.getBody().getData().status();
    }

    private String createVerifySign(NotoficationReceiveDto receiveDto, Order order) {
        String json = "{\"sessionId\":\"" + createSessionId(order) +
                "\",\"orderId\":" + receiveDto.getOrderId() +
                ",\"amount\":" + order.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\"" +
                ",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";
        return DigestUtils.sha384Hex(json);
    }

    private void validate(NotoficationReceiveDto receiveDto, Order order) {
        validateField(config.getMerchantId().equals(receiveDto.getMerchantId()));
        validateField(config.getPosId().equals(receiveDto.getPosId()));
        validateField(createSessionId(order).equals(receiveDto.getSessionId()));
        validateField(order.getGrossValue().compareTo(BigDecimal.valueOf(receiveDto.getAmount()).movePointLeft(2)) == 0);
        validateField(order.getGrossValue().compareTo(BigDecimal.valueOf(receiveDto.getOriginAmount()).movePointLeft(2)) == 0);
        validateField("PLN" .equals(receiveDto.getCurrency()));
        validateField(createReceivedSign(receiveDto, order).equals(receiveDto.getSign()));

    }

// NotoficationReceiveDto(merchantId=214066, posId=214066, sessionId=order_id_3, amount=22799, originAmount=22799,
// currency=PLN, orderId=318154845, methodId=270, statement=p24-D15-B48-J45,
// sign=a2fb3128e5ffb5679cfe57e884d2f8822de76c15d7dfdff47fe9a81a33420af4d4e91483005a1c6200e6733062101d63)
// createReceivedSign: {"merchantId":214066,"posId":214066,"sessionId":"order_id_3","amount":22799,"originAmount":22799,
// "currency":"PLN", "orderId":318154845,"methodId":270,"statement":"p24-D15-B48-J45","crc":"204dd9211ac9160d"}
// 9861108cf65c68057b3e14e645844337bbad1689cb45e730291910f32ed59fbf4f4be0b79ac1e2d41e40569f8b8cd17a
    private String createReceivedSign(NotoficationReceiveDto receiveDto, Order order) {
        String json = "{\"merchantId\":" + config.getMerchantId() +
                ",\"posId\":" + config.getPosId() +
                ",\"sessionId\":\"" + createSessionId(order) +
                "\",\"amount\":" + order.getGrossValue().movePointRight(2).intValue() +
                ",\"originAmount\":" + order.getGrossValue().movePointRight(2).intValue() +
                ",\"currency\":\"PLN\"" +
                ", \"orderId\":" + receiveDto.getOrderId() +
                ",\"methodId\":" + receiveDto.getMethodId() +
                ",\"statement\":\"" + receiveDto.getStatement() +
                "\",\"crc\":\"" + (config.isTestMode() ? config.getTestCrc() : config.getCrc()) + "\"}";
        log.info("createReceivedSign: " + json);
        String sha = DigestUtils.sha384Hex(json);
        log.info(sha);
        return sha;
    }

    private void validateField(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Walidacja niepoprawna");
        }
    }
}
