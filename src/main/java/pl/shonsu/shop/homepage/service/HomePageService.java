package pl.shonsu.shop.homepage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.shonsu.shop.common.model.Product;
import pl.shonsu.shop.common.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePageService {
    private final ProductRepository productRepository;

    public List<Product> getSaleProtucts() {
        return productRepository.findByInSalePlaceTrue();
    }
}
