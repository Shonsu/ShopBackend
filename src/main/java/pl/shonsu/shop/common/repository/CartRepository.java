package pl.shonsu.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.shonsu.shop.common.model.Cart;

import java.time.LocalDateTime;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByCreatedLessThan(LocalDateTime minusDays);

    @Modifying
    @Query("delete from Cart c where c.id=:cartId" )
    void deleteCartById(Long cartId);

    @Modifying
    @Query("delete from Cart c where c.id in (:ids)" )
    void deleteCartByIdIn(List<Long> ids);
}
