package pl.shonsu.shop.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.product.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
