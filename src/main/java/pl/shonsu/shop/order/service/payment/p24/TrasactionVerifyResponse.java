package pl.shonsu.shop.order.service.payment.p24;

import lombok.Getter;

@Getter
public class TrasactionVerifyResponse {
    private Data data;

    record Data(String status) {
    }
}
