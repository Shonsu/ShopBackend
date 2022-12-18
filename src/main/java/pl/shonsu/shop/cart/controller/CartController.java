package pl.shonsu.shop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shonsu.shop.cart.controller.dto.CartSummaryDto;
import pl.shonsu.shop.cart.mapper.CartMapper;
import pl.shonsu.shop.cart.model.dto.CartProductDto;
import pl.shonsu.shop.cart.service.CartService;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{id}")
    public CartSummaryDto getCart(@PathVariable Long id) {
        return CartMapper.mapToCartSummary(cartService.getCart(id));
    }

    @PutMapping("/{id}")
    public CartSummaryDto addProductToCart(@PathVariable Long id, @RequestBody CartProductDto cartProductDto) {
        return CartMapper.mapToCartSummary(cartService.addProductToCart(id, cartProductDto));
    }
}
