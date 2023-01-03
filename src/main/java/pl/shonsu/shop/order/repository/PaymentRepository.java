package pl.shonsu.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.order.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
