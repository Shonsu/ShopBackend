package pl.shonsu.shop.cart.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CartSummaryDto {
    private Long id;
    private List<CartuSummaryItemDto> items;
    private SummaryDto summary;
}
