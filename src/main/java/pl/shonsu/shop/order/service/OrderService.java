package pl.shonsu.shop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.common.mail.EmailClientService;
import pl.shonsu.shop.common.model.Cart;
import pl.shonsu.shop.common.model.OrderStatus;
import pl.shonsu.shop.common.repository.CartItemRepository;
import pl.shonsu.shop.common.repository.CartRepository;
import pl.shonsu.shop.order.model.Order;
import pl.shonsu.shop.order.model.OrderLog;
import pl.shonsu.shop.order.model.Payment;
import pl.shonsu.shop.order.model.PaymentType;
import pl.shonsu.shop.order.model.Shipment;
import pl.shonsu.shop.order.model.dto.NotoficationReceiveDto;
import pl.shonsu.shop.order.model.dto.OrderDto;
import pl.shonsu.shop.order.model.dto.OrderListDto;
import pl.shonsu.shop.order.model.dto.OrderSummary;
import pl.shonsu.shop.order.repository.OrderLogRepository;
import pl.shonsu.shop.order.repository.OrderRepository;
import pl.shonsu.shop.order.repository.OrderRowRepository;
import pl.shonsu.shop.order.repository.PaymentRepository;
import pl.shonsu.shop.order.repository.ShipmentRepository;
import pl.shonsu.shop.order.service.payment.p24.PaymentMethodP24;

import java.time.LocalDateTime;
import java.util.List;

import static pl.shonsu.shop.order.service.mapper.OrderDtoMapper.mapToOrderListDto;
import static pl.shonsu.shop.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.createNewOrder;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderLogRepository orderLogRepository;

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;
    private final PaymentMethodP24 paymentMethodP24;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto, Long userId) {
        // pobranie koszyka
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        //stworzenie zamówienia z wierszami

        // zapisać zamówienie
        Order newOrder = orderRepository.save(createNewOrder(orderDto, cart, shipment, payment, userId));
        savedOrderRows(cart, newOrder.getId(), shipment);
        clearOrderCart(orderDto);
        sendConfirmEmail(newOrder);
        String redirectUrl = initPaymentIfNeeded(newOrder);
        // zwróć podsumowanie
        return createOrderSummary(payment, newOrder, redirectUrl);
    }

    private String initPaymentIfNeeded(Order newOrder) {
        if (newOrder.getPayment().getType() == PaymentType.P24_ONLINE) {
            return paymentMethodP24.initPayment(newOrder);
        }
        return null;
    }

    private void sendConfirmEmail(Order newOrder) {
        emailClientService.getInstance().send(newOrder.getEmail(),
                "Twoje zamówienie zostało przyjęte",
                createEmailMessage(newOrder));
    }

    private void clearOrderCart(OrderDto orderDto) {
        // usunąć koszyk
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
    }

    private void savedOrderRows(Cart cart, Long orderId, Shipment shipment) {
        saveProductRows(cart, orderId);
        saveShipmentRow(orderId, shipment);
    }

    private void saveShipmentRow(Long orderId, Shipment shipment) {
        orderRowRepository.save(mapToOrderRow(orderId, shipment));
    }

    private void saveProductRows(Cart cart, Long orderId) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRowWithQuantity(orderId, cartItem)
                )
                .peek(orderRowRepository::save)
                .toList();
    }

    public List<OrderListDto> getOrdersForCustomer(Long userId) {
        return mapToOrderListDto(orderRepository.findByUserId(userId));
    }

    public Order getOrderByOrderHash(String orderHash) {
        return orderRepository.findByOrderHash(orderHash).orElseThrow();
    }

    @Transactional
    public void receiveNotification(String orderHash, NotoficationReceiveDto receiveDto) {
        Order order = getOrderByOrderHash(orderHash);
        String status = paymentMethodP24.receiveNotification(order, receiveDto);
        if (status.equals("success")) {
            OrderStatus oldStatus = order.getOrderStatus();
            order.setOrderStatus(OrderStatus.PAID);
            orderLogRepository.save(OrderLog.builder()
                    .created(LocalDateTime.now())
                    .orderId(order.getId())
                    .note("Opłacono zamówienie przez Przelewy24, id płatnośći: " +
                            receiveDto.getStatement() +
                            " , zmieniono status z " + oldStatus.getValue() + " na " +
                            order.getOrderStatus().getValue())
                    .build());
        }
        //aktualizacja zamówienia
    }
}
