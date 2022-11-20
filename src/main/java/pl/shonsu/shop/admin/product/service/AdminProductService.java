package pl.shonsu.shop.admin.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.shonsu.shop.admin.product.repository.AdminProductRepository;
import pl.shonsu.shop.admin.product.model.AdminProduct;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final AdminProductRepository adminProductRepository;

    public Page<AdminProduct> getProducts(Pageable pageable) {
        return adminProductRepository.findAll(pageable);
    }

    public AdminProduct getProduct(Long id) {
        return adminProductRepository.findById(id).orElseThrow();
    }

    public AdminProduct createProduct(AdminProduct product) {
        return adminProductRepository.save(product);
    }

    public AdminProduct updateProduct(AdminProduct product) {
        return adminProductRepository.save(product);
    }

    public void deleteProduct(Long id) {
        adminProductRepository.deleteById(id);
    }
}
