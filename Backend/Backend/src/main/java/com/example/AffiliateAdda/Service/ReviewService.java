package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.DTO.ReviewDTO;
import com.example.AffiliateAdda.Model.*;
import com.example.AffiliateAdda.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private TrackerRepository trackerRepository;

    // Method to submit a new review or update an existing review
    public ReviewDTO submitReview(Long productId, Long userId, int rating, String reviewText) {

        //debug
        System.out.println("inside submitReview - service");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if(!product.isActive()){
            throw new IllegalArgumentException("Product is not active");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(!user.isActive()){
            throw new IllegalArgumentException("User is not active");
        }

        Tracker tracker = trackerRepository.findByUserAndProduct(user, product);
        if(tracker == null){
            throw new IllegalArgumentException("Tracker not found - Link is not generated");
        }

        // Change the rating of product
        ProductDetail productDetail = product.getProductDetail();
        int newRating = (int) ((productDetail.getRating() * productDetail.getRatingCount() + rating) / (productDetail.getRatingCount() + 1));
        productDetail.setRating(newRating);


        // Check if the user has already reviewed the product
        Optional<Review> existingReview = reviewRepository.findByUserAndProduct(user, product);
        if (existingReview.isPresent()) {
            // Update existing review
            Review review = existingReview.get();
            review.setRating(rating);
            review.setReviewText(reviewText);
            review.setReviewDate(LocalDateTime.now());

            productDetailRepository.save(productDetail);

            Review savedReview = reviewRepository.save(review);
            ReviewDTO reviewDTO = new ReviewDTO(savedReview);

            return reviewDTO;
        } else {
            // Create new review
            Review newReview = new Review();
            newReview.setProduct(product);
            newReview.setUser(user);
            newReview.setRating(rating);
            newReview.setReviewText(reviewText);
            newReview.setReviewDate(LocalDateTime.now());

            productDetail.setRatingCount(productDetail.getRatingCount() + 1);
            productDetailRepository.save(productDetail);

            Review savedReview = reviewRepository.save(newReview);
            ReviewDTO reviewDTO = new ReviewDTO(savedReview);

            return reviewDTO;
        }
    }
}