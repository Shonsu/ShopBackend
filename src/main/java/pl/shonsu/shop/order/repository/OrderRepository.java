package pl.shonsu.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
