package pl.shonsu.shop.order.service.payment.p24;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TransactionRegisterResponse {
    private Data data;
    record Data(String token){}
}
