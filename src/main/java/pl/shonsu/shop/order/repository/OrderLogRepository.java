package pl.shonsu.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.order.model.OrderLog;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
}
