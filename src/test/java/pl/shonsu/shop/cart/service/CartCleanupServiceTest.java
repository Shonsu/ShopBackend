package pl.shonsu.shop.cart.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.shonsu.shop.cart.model.Cart;
import pl.shonsu.shop.cart.model.CartItem;
import pl.shonsu.shop.cart.repository.CartItemRepository;
import pl.shonsu.shop.cart.repository.CartRepository;
import pl.shonsu.shop.common.model.Product;
import pl.shonsu.shop.common.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CartCleanupServiceTest {

    @Autowired
    CartCleanupService cleanupService;

    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    @Sql(value = {"/test/01-CleanupServiceTest.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/test/clean_database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldCleanupOldCarts() {
        //given
        createCartsWithItems();
        //when
        cleanupService.cleanupOldCarts();
        //then
        List<Cart> cartList = cartRepository.findAll();
        List<CartItem> cartItems = cartItemRepository.findAll();
        assertEquals(1, cartList.size());
        assertEquals(1, cartItems.size());
    }

    private void createCartsWithItems() {

        List<Cart> carts = new ArrayList<>(Arrays.asList(
                new Cart(1L, LocalDateTime.now().minusDays(4), null),
                new Cart(2L, LocalDateTime.now().minusDays(4), null),
                new Cart(3L, LocalDateTime.now().minusDays(4), null),
                new Cart(4L, LocalDateTime.now().minusDays(1), null)
        ));
        cartRepository.saveAll(carts);
        List<Product> products = productRepository.findAll();

        List<CartItem> cartItems = new ArrayList<>(Arrays.asList(
                new CartItem(1L, 2, products.get(0), 1L),
                new CartItem(2L, 2, products.get(1), 2L),
                new CartItem(3L, 2, products.get(2), 3L),
                new CartItem(4L, 2, products.get(0), 4L)
        ));
        cartItemRepository.saveAll(cartItems);
    }
}