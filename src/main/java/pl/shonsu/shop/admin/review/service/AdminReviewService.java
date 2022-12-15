package pl.shonsu.shop.admin.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.admin.review.model.AdminReview;
import pl.shonsu.shop.admin.review.model.AdminReviewUpdateDto;
import pl.shonsu.shop.admin.review.repository.AdminReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {
    private final AdminReviewRepository adminReviewRepository;

    public List<AdminReview> getReviews() {
        return adminReviewRepository.findAllByOrderByIdDesc();
    }

    public void deleteReview(Long id) {
        adminReviewRepository.deleteById(id);
    }

    public AdminReview updateReview(AdminReview review) {
        return adminReviewRepository.save(review);
    }

    public AdminReview getReview(Long id) {
        return adminReviewRepository.findById(id).orElseThrow();
    }

    public AdminReview updateAuthorNameAndContent(Long id, AdminReviewUpdateDto adminReviewUpdateDto) {
        AdminReview review = adminReviewRepository.findById(id).orElseThrow();
        return adminReviewRepository.save(AdminReview.builder()
                .id(id)
                .productId(review.getProductId())
                .moderated(review.isModerated())
                .authorName(adminReviewUpdateDto.authorName())
                .content(adminReviewUpdateDto.content())
                .build());
    }

    @Transactional
    public void moderate(Long id) {
        adminReviewRepository.moderate(id);
    }
}
