package com.example.AffiliateAdda.Model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Component
@Table(name= "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_swq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    Long productId;

    String productName;
    String productBaseurl;
    String type;
    String subType;
    double perClickPrice = 0L;
    Long clickCount;
    double perBuyPrice = 0L;
    Long buyCount = 0L;
    boolean active = true;
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    ProductDetail productDetail;

    @OneToMany(mappedBy = "product")
    private List<Tracker> trackers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    public Product() {}

    public Product(Long productId, String productName, String productBaseurl, String type, String subType, double perClickPrice, Long clickCount, double perBuyPrice, Long buyCount, boolean active, ProductDetail productDetail, List<Tracker> trackers, User user, List<Review> reviews) {
        this.productId = productId;
        this.productName = productName;
        this.productBaseurl = productBaseurl;
        this.type = type;
        this.subType = subType;
        this.perClickPrice = perClickPrice;
        this.clickCount = clickCount;
        this.perBuyPrice = perBuyPrice;
        this.buyCount = buyCount;
        this.active = active;
        this.productDetail = productDetail;
        this.trackers = trackers;
        this.user = user;
        this.reviews = reviews;
    }

    public Product(Long productId, String productName, String productBaseurl, double perClickPrice, String type, double perBuyPrice, List<Tracker> trackers, User user) {
        this.productId = productId;
        this.productName = productName;
        this.productBaseurl = productBaseurl;
        this.perClickPrice = perClickPrice;
        this.type = type;
        this.perBuyPrice = perBuyPrice;
        this.trackers = trackers;
        this.user = user;
    }

    public Product(Long productId, String productName, String productBaseurl, double perClickPrice, String type, List<Tracker> trackers, User user) {
        this.productId = productId;
        this.productName = productName;
        this.productBaseurl = productBaseurl;
        this.perClickPrice = perClickPrice;
        this.type = type;
        this.trackers = trackers;
        this.user = user;
    }

    public Product(Long productId, String productName, String productBaseurl, String type, double perClickPrice, double perBuyPrice, boolean active, List<Tracker> trackers, User user) {
        this.productId = productId;
        this.productName = productName;
        this.productBaseurl = productBaseurl;
        this.type = type;
        this.perClickPrice = perClickPrice;
        this.perBuyPrice = perBuyPrice;
        this.active = active;
        this.trackers = trackers;
        this.user = user;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Long buyCount) {
        this.buyCount = buyCount;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getPerClickPrice() {
        return perClickPrice;
    }

    public void setPerClickPrice(double perClickPrice) {
        this.perClickPrice = perClickPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tracker> getTrackers() {
        return trackers;
    }

    public void setTrackers(List<Tracker> trackers) {
        this.trackers = trackers;
    }

    public String getProductBaseurl() {
        return productBaseurl;
    }

    public void setProductBaseurl(String productBaseurl) {
        this.productBaseurl = productBaseurl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String productType) {
        this.type = productType;
    }

    public double getPerBuyPrice() {
        return perBuyPrice;
    }

    public void setPerBuyPrice(double perBuyPrice) {
        this.perBuyPrice = perBuyPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
