package com.example.AffiliateAdda.DTO;

public class ReviewSubmitDto {
    private Long productId;
    private String username; // To match with sessionStorage data
    private int rating;      // Rating (1-5)
    private String reviewText; // The review content

    // Getters and Setters


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
