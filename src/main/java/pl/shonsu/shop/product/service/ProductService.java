package pl.shonsu.shop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.shonsu.shop.common.model.Product;
import pl.shonsu.shop.common.model.Review;
import pl.shonsu.shop.common.repository.ProductRepository;
import pl.shonsu.shop.common.repository.ReviewRepository;
import pl.shonsu.shop.product.service.dto.ProductDto;
import pl.shonsu.shop.product.service.dto.ReviewDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ProductDto getProductsBySlug(String slug) {
        Product product = productRepository.findBySlug(slug);
        List<Review> reviews = reviewRepository.findByProductIdAndModeratedTrue(product.getId());
        return mapToProductDto(product, reviews);
    }

    private static ProductDto mapToProductDto(Product product, List<Review> reviews) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .description(product.getDescription())
                .fullDescription(product.getFullDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .image(product.getImage())
                .slug(product.getSlug())
                .reviews(reviews.stream()
                        .map(ProductService::mapToReviewDto
                        ).toList()
                )
                .build();
    }

    private static ReviewDto mapToReviewDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .authorName(review.getAuthorName())
                .content(review.getContent())
                .moderate(review.isModerated())
                .build();
    }
}
