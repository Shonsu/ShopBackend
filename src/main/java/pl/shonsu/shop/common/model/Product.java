package pl.shonsu.shop.common.model;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Column(name = "fulldescription")
    private String fullDescription;
    private BigDecimal price;
    private BigDecimal salePrice;
    private String currency;
    private String image;
    private String slug;

    public Product(Long id, String name, Long categoryId, String description, String fullDescription, BigDecimal price, BigDecimal salePrice, String currency, String image, String slug) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.description = description;
        this.fullDescription = fullDescription;
        this.price = price;
        this.salePrice = salePrice;
        this.currency = currency;
        this.image = image;
        this.slug = slug;
    }

    public Product() {
    }

    public BigDecimal getEndPrice() {
        return salePrice != null ? salePrice : price;
    }
//    @OneToMany
//    @JoinColumn(name = "productId")
//    private List<Review> reviews;
}
