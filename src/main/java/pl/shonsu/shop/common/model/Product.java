package pl.shonsu.shop.common.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    @Column(name = "fulldescription" )
    private String fullDescription;
    private BigDecimal price;
    private String currency;
    private String image;
    private String slug;

    public Product(Long id, String name, Long categoryId, String description, String fullDescription, BigDecimal price, String currency, String image, String slug) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.description = description;
        this.fullDescription = fullDescription;
        this.price = price;
        this.currency = currency;
        this.image = image;
        this.slug = slug;
    }

    public Product() {
    }
//    @OneToMany
//    @JoinColumn(name = "productId")
//    private List<Review> reviews;
}
