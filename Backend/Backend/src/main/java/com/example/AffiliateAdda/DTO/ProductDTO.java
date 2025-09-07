package com.example.AffiliateAdda.DTO;

import java.util.List;

public class ProductDTO {
    private Long productId;
    private String productName;
    private double perClickPrice;
    private String productType;
    private String productSubType;
    private double perBuyPrice;
    private String description;
    private String shortDescription;
    private String tags;
    private int rating;
    private Long ratingCount;
    private List<ReviewDTO> reviews;

    public ProductDTO(Long productId, String productName, double perClickPrice, String productType, String productSubType, double perBuyPrice, String description, String shortDescription, String tags, int rating, Long ratingCount, List<ReviewDTO> reviews) {
        this.productId = productId;
        this.productName = productName;
        this.perClickPrice = perClickPrice;
        this.productType = productType;
        this.productSubType = productSubType;
        this.perBuyPrice = perBuyPrice;
        this.description = description;
        this.shortDescription = shortDescription;
        this.tags = tags;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.reviews = reviews;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPerClickPrice() {
        return perClickPrice;
    }

    public void setPerClickPrice(double perClickPrice) {
        this.perClickPrice = perClickPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductSubType() {
        return productSubType;
    }

    public void setProductSubType(String productSubType) {
        this.productSubType = productSubType;
    }

    public double getPerBuyPrice() {
        return perBuyPrice;
    }

    public void setPerBuyPrice(double perBuyPrice) {
        this.perBuyPrice = perBuyPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }
}
