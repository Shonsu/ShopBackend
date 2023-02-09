package pl.shonsu.shop.admin.order.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.shonsu.shop.admin.order.model.AdminOrderStatus;
import pl.shonsu.shop.admin.order.model.dto.AdminOrderStats;
import pl.shonsu.shop.common.mail.EmailClientService;
import pl.shonsu.shop.common.mail.FakeEmailService;
import pl.shonsu.shop.common.model.Cart;
import pl.shonsu.shop.common.model.CartItem;
import pl.shonsu.shop.common.model.Product;
import pl.shonsu.shop.common.repository.CartRepository;
import pl.shonsu.shop.order.model.Payment;
import pl.shonsu.shop.order.model.PaymentType;
import pl.shonsu.shop.order.model.Shipment;
import pl.shonsu.shop.order.model.dto.OrderDto;
import pl.shonsu.shop.order.repository.OrderRepository;
import pl.shonsu.shop.order.repository.PaymentRepository;
import pl.shonsu.shop.order.repository.ShipmentRepository;
import pl.shonsu.shop.order.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdminOrderStatsServiceIT {

    @MockBean
    ShipmentRepository shipmentRepository;

    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    CartRepository cartRepository;

    @MockBean
    EmailClientService emailClientService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    AdminOrderStatsService adminOrderStatsService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldRetrunOrderStatsBetweenDates() {
        //given
        LocalDateTime from = LocalDateTime.of(2023, 2, 7, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2023, 2, 8, 23, 59, 59);
        when(cartRepository.findById(any())).thenReturn(createCart());
        when(shipmentRepository.findById(any())).thenReturn(createShipment());
        when(paymentRepository.findById(any())).thenReturn(createPayment());
        when(emailClientService.getInstance()).thenReturn(new FakeEmailService());
        createAdminOrderList();
        //when
        AdminOrderStats adminOrderStats = adminOrderStatsService.getStatistics(from, to, AdminOrderStatus.NEW);
        System.out.println(adminOrderStats);
        //then
        assertThat(adminOrderStats.getOrder().size()).isEqualTo(1);
        assertThat(adminOrderStats.getPlaceDate().get(0)).isEqualTo(LocalDate.of(2023, 2, 7));
        assertThat(adminOrderStats.getSale().get(0)).isEqualTo(new BigDecimal("26"));

    }

    private Optional<Payment> createPayment() {
        return Optional.of(Payment.builder()
                .id(3L)
                .name("test payment")
                .type(PaymentType.BANK_TRANSFER)
                .defaultPayment(true)
                .build());
    }

    private Optional<Shipment> createShipment() {
        return Optional.of(Shipment.builder()
                .id(2L)
                .price(new BigDecimal("15"))
                .build()
        );
    }

    private void createAdminOrderList() {
        orderService.placeOrder(OrderDto.builder()
                .firstname("Mirek")
                .lastname("Głowacki")
                .street("Reymonta")
                .zipcode("50-500")
                .city("Lubiatów")
                .email("glowam@o2.pl")
                .phone("555-555-555")
                .cartId(1L)
                .shipmentId(2L)
                .paymentId(3L)
                .build());
    }

    private Optional<Cart> createCart() {
        return Optional.of(
                Cart.builder()
                        .id(1L)
                        .created(LocalDateTime.now())
                        .items(createItems())
                        .build()
        );
    }

    private List<CartItem> createItems() {
        return List.of(
                CartItem.builder()
                        .id(1L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(1L)
                                .price(new BigDecimal("11"))
                                .build())
                        .build(),
                CartItem.builder()
                        .id(2L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(1L)
                                .price(new BigDecimal("11"))
                                .build())
                        .build()
        );
    }
}
