package pl.shonsu.shop.review.controller;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shonsu.shop.common.model.Review;
import pl.shonsu.shop.review.controller.dto.ReviewDto;
import pl.shonsu.shop.review.service.ReviewService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto) {
        return reviewService.addReview(Review.builder()
                .authorName(clearContent(reviewDto.authorName()))
                .content(clearContent(reviewDto.content()))
                .productId(reviewDto.productId())
                .build());
    }

    private String clearContent(String text) {
        return Jsoup.clean(text, Safelist.none());
    }

}
