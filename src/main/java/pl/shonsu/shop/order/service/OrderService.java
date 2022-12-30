package pl.shonsu.shop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.common.model.Cart;
import pl.shonsu.shop.common.model.CartItem;
import pl.shonsu.shop.common.repository.CartItemRepository;
import pl.shonsu.shop.common.repository.CartRepository;
import pl.shonsu.shop.order.model.Order;
import pl.shonsu.shop.order.model.OrderRow;
import pl.shonsu.shop.order.model.OrderStatus;
import pl.shonsu.shop.order.model.dto.OrderDto;
import pl.shonsu.shop.order.model.dto.OrderSummary;
import pl.shonsu.shop.order.repository.OrderRepository;
import pl.shonsu.shop.order.repository.OrderRowRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;

    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto) {
        // pobranie koszyka
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        //stworzenie zamówienia z wierszami
        Order order = Order.builder()
                .firstname(orderDto.getFirstname())
                .lastname(orderDto.getLastname())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems()))
                .build();

        // zapisać zamówienie
        Order newOrder = orderRepository.save(order);
        savedOrderRows(cart, newOrder.getId());
        // usunąć koszyk
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
        // zwróć podsumowanie
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .build();
    }

    private BigDecimal calculateGrossValue(List<CartItem> items) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(
                        BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private void savedOrderRows(Cart cart, Long id) {
        List<OrderRow> orderRows = cart.getItems().stream()
                .map(cartItem -> OrderRow.builder()
                        .quantity(cartItem.getQuantity())
                        .productId(cartItem.getProduct().getId())
                        .price(cartItem.getProduct().getPrice())
                        .orderId(id)
                        .build()
                )
                .peek(orderRowRepository::save)
                .toList();

    }
}
