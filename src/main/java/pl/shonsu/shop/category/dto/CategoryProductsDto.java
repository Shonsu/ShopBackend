package pl.shonsu.shop.category.dto;

import org.springframework.data.domain.Page;
import pl.shonsu.shop.common.dto.ProductListDto;
import pl.shonsu.shop.common.model.Category;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
