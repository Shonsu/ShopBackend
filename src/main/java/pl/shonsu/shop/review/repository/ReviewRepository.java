package pl.shonsu.shop.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shonsu.shop.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
