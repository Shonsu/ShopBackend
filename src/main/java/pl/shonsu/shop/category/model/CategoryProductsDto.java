package pl.shonsu.shop.category.model;

import org.springframework.data.domain.Page;
import pl.shonsu.shop.product.model.Product;

public record CategoryProductsDto(Category category, Page<Product> products) {
}
