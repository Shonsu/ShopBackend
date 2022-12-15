package pl.shonsu.shop.admin.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.shonsu.shop.admin.review.controller.dto.AdminReviewDto;
import pl.shonsu.shop.admin.review.model.AdminReview;
import pl.shonsu.shop.admin.review.model.AdminReviewUpdateDto;
import pl.shonsu.shop.admin.review.service.AdminReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/reviews")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @PatchMapping("/{id}/moderate")
    public void moderate(@PathVariable Long id) {
        adminReviewService.moderate(id);
    }

    @PatchMapping("/{id}")
    public AdminReview moderate(@PathVariable Long id, @RequestBody AdminReviewUpdateDto adminReviewUpdateDto) {
        return adminReviewService.updateAuthorNameAndContent(id, adminReviewUpdateDto);
    }

    @PutMapping("/{id}")
    public AdminReview updateReview(@RequestBody AdminReviewDto adminReviewDto, @PathVariable Long id) {
        return adminReviewService.updateReview(
                AdminReview.builder()
                        .id(id)
                        .productId(adminReviewDto.productId())
                        .authorName(adminReviewDto.authorName())
                        .content(adminReviewDto.content())
                        .moderated(adminReviewDto.moderated())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        adminReviewService.deleteReview(id);
    }

    @GetMapping
    public List<AdminReview> getReviews() {
        return adminReviewService.getReviews();
    }

    @GetMapping("/{id}")
    public AdminReview getReview(@PathVariable Long id) {
        return adminReviewService.getReview(id);
    }
}
