package pl.shonsu.shop.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    @Column(name = "fulldescription")
    private String fullDescription;
    private BigDecimal price;
    private String currency;
    private String image;
    private String slug;
//    @OneToMany
//    @JoinColumn(name = "productId")
//    private List<Review> reviews;
}
