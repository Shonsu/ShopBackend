package pl.shonsu.shop.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.common.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySlug(String slug);

    Page<Product> findByCategoryId(Long id, Pageable pageable);
}
