package pl.shonsu.shop.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.common.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductIdAndModeratedTrue(Long id);
}
