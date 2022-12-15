package pl.shonsu.shop.admin.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.shonsu.shop.admin.review.model.AdminReview;

import java.util.List;

public interface AdminReviewRepository extends JpaRepository<AdminReview, Long> {

    @Modifying
    @Query("update AdminReview r set r.moderated=true where r.id=:id")
    void moderate(Long id);

    List<AdminReview> findAllByOrderByIdDesc();

    List<AdminReview> findAllByOrderByModeratedAsc();
}
