package pl.shonsu.shop.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.common.model.Cart;
import pl.shonsu.shop.common.repository.CartItemRepository;
import pl.shonsu.shop.common.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartCleanupService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Scheduled(cron = "${app.cart.cleanup.expression}" )
    public void cleanupOldCarts() {

        List<Cart> carts = cartRepository.findByCreatedLessThan(LocalDateTime.now().minusDays(3));
        List<Long> ids = carts.stream().map(Cart::getId).toList();
        if (!ids.isEmpty()) {
            cartItemRepository.deleteAllByCartIdIn(ids);
            cartRepository.deleteCartByIdIn(ids);
        }
//        carts.forEach(cart -> {
//            cartItemRepository.deleteByCartId(cart.getId());
//            cartRepository.deleteCartById(cart.getId());
//        });
    }
}
