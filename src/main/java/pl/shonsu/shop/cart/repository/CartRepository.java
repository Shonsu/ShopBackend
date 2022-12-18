package pl.shonsu.shop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
