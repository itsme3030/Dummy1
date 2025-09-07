package com.example.AffiliateAdda.Model;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Entity
@Component
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";

    @Column(nullable = false)
    private boolean active = true;

    private DateTime creationDate;

    @OneToMany(mappedBy = "user")
    private List<Tracker> trackers;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetails userDetail;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    public User() {
    }

    public User(Long id, String username, String password, String role, List<Tracker> trackers, List<Product> products) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.trackers = trackers;
        this.products = products;
    }

    public User(Long id, String username, String password, List<Tracker> trackers, List<Product> products) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.trackers = trackers;
        this.products = products;
    }

    public User(Long id, String username, String password, String role, boolean active, List<Tracker> trackers, List<Product> products) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
        this.trackers = trackers;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<Tracker> getTrackers() {
        return trackers;
    }

    public void setTrackers(List<Tracker> trackers) {
        this.trackers = trackers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public UserDetails getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetails userDetail) {
        this.userDetail = userDetail;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
