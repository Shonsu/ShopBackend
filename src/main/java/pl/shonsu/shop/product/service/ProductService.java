package pl.shonsu.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.shonsu.shop.common.model.Product;
import pl.shonsu.shop.common.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductsBySlug(String slug) {
        return productRepository.findBySlug(slug)
                .orElseThrow();
    }
}
