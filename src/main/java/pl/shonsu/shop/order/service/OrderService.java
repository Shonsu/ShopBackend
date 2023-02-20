package pl.shonsu.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.common.mail.EmailClientService;
import pl.shonsu.shop.common.model.Cart;
import pl.shonsu.shop.common.repository.CartItemRepository;
import pl.shonsu.shop.common.repository.CartRepository;
import pl.shonsu.shop.order.model.Order;
import pl.shonsu.shop.order.model.Payment;
import pl.shonsu.shop.order.model.Shipment;
import pl.shonsu.shop.order.model.dto.OrderDto;
import pl.shonsu.shop.order.model.dto.OrderSummary;
import pl.shonsu.shop.order.repository.OrderRepository;
import pl.shonsu.shop.order.repository.OrderRowRepository;
import pl.shonsu.shop.order.repository.PaymentRepository;
import pl.shonsu.shop.order.repository.ShipmentRepository;

import static pl.shonsu.shop.order.service.mapper.OrderEmailMessageMapper.createEmailMessage;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.createNewOrder;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.shonsu.shop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;

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
        // zwróć podsumowanie
        return createOrderSummary(payment, newOrder);
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

}
