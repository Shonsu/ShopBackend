package pl.shonsu.shop.admin.review.controller.dto;

import org.hibernate.validator.constraints.Length;

public record AdminReviewDto(
        @Length(min = 2, max = 60) String authorName,
        @Length(min = 4, max = 600) String content,
        Long productId,
        boolean moderated) {
}