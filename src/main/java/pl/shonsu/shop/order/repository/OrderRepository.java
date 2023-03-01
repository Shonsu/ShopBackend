package pl.shonsu.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    Optional<Order> findByOrderHash(String orderHash);
}
