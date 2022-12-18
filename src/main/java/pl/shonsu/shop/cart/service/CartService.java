package pl.shonsu.shop.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.cart.model.Cart;
import pl.shonsu.shop.cart.model.CartItem;
import pl.shonsu.shop.cart.model.dto.CartProductDto;
import pl.shonsu.shop.cart.repository.CartRepository;
import pl.shonsu.shop.common.model.Product;
import pl.shonsu.shop.common.repository.ProductRepository;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Cart addProductToCart(Long id, CartProductDto cartProductDto) {
        Cart cart = getInitializedCart(id);
        cart.addProduct(CartItem.builder()
                .quantity(cartProductDto.quantity())
                .product(getProduct(cartProductDto.productId()))
                .cartId(cart.getId())
                .build());
        return cart;
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    private Cart getInitializedCart(Long id) {
        if (id == null || id <= 0) {
            return cartRepository.save(Cart.builder().created(now()).build());
        }
        return cartRepository.findById(id).orElseThrow();
    }
}
