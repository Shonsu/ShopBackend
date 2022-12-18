package pl.shonsu.shop.cart.model;

import lombok.*;
import pl.shonsu.shop.common.model.Product;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @OneToOne
    private Product product;
    private Long cartId;
}
