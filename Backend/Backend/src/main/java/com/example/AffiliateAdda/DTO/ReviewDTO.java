package com.example.AffiliateAdda.DTO;

import com.example.AffiliateAdda.Model.Review;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long reviewId;
    private int rating;
    private String reviewText;
    private LocalDateTime reviewDate;
    private String username; // We only need the username field here

    // Constructor to initialize all the fields
    public ReviewDTO(Review review) {
        this.reviewId = review.getReviewId();
        this.rating = review.getRating();
        this.reviewText = review.getReviewText();
        this.reviewDate = review.getReviewDate();
        this.username = review.getUser() != null ? review.getUser().getUsername() : null;
    }

    // Getters and setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
