package pl.shonsu.shop.cart.model;

import org.junit.jupiter.api.Test;
import pl.shonsu.shop.common.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

    @Test
    void ShouldIncrementQuantityWhenAddedProductExistsInCart() {
        //given
        Long cartItemId = 1L;
        Cart cart = new Cart();
        CartItem cartItem = CartItem.builder()
                .product(Product.builder().id(cartItemId).build())
                .quantity(1)
                .build();

        //when
        cart.addProduct(cartItem);
        cart.addProduct(cartItem);
        int result = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId() == cartItemId)
                .findFirst().get().getQuantity();

        //then
        assertEquals(2, result);
    }
}