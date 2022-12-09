package pl.shonsu.shop.category.model;

import org.springframework.data.domain.Page;
import pl.shonsu.shop.product.controller.dto.ProductListDto;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
