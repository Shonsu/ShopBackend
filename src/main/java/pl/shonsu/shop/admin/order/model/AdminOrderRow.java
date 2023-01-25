package pl.shonsu.shop.admin.order.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "order_row")
public class AdminOrderRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private Long shipmentId;
}
