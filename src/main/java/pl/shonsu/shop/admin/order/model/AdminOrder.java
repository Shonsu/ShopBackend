package pl.shonsu.shop.admin.order.model;

import lombok.Getter;
import lombok.Setter;
import pl.shonsu.shop.order.model.OrderRow;
import pl.shonsu.shop.order.model.Payment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
public class AdminOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime placeDate;
    @Enumerated(EnumType.STRING)
    private AdminOrderStatus orderStatus;
    @OneToMany
    @JoinColumn(name = "orderId")
    private List<OrderRow> orderRows;
    private BigDecimal grossValue;
    private String firstname;
    private String lastname;
    private String street;
    private String zipcode;
    private String city;
    private String email;
    private String phone;
    @OneToOne
    private Payment payment;
}
