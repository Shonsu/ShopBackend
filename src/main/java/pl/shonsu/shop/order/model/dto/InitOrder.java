package pl.shonsu.shop.order.model.dto;

import lombok.Builder;
import lombok.Getter;
import pl.shonsu.shop.order.model.Payment;
import pl.shonsu.shop.order.model.Shipment;

import java.util.List;

@Getter
@Builder
public class InitOrder {
    private List<Shipment> shipment;
    private List<Payment> payment;
}
