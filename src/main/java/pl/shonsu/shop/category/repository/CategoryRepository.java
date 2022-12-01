package pl.shonsu.shop.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
