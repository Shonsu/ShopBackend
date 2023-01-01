package pl.shonsu.shop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.order.model.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
