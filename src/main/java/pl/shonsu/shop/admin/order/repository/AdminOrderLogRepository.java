package pl.shonsu.shop.admin.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.admin.order.model.AdminOrderLog;

public interface AdminOrderLogRepository extends JpaRepository <AdminOrderLog, Long> {
}
