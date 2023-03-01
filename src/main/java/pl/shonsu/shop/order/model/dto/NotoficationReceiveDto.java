package pl.shonsu.shop.order.model.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NotoficationReceiveDto {

    private Integer merchantId;
    private Integer posId;
    private String sessionId;
    private Integer amount;
    private Integer originAmount;
    private String currency;
    private Integer orderId;
    private Integer methodId;
    private String statement;
    private String sign;
}

