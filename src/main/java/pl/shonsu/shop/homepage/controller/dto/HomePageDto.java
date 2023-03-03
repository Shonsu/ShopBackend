package pl.shonsu.shop.homepage.controller.dto;

import pl.shonsu.shop.common.model.Product;

import java.util.List;

public record HomePageDto (List<Product> saleProducts){
}
