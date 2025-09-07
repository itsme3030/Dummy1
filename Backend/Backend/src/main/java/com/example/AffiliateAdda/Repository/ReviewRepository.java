package com.example.AffiliateAdda.Repository;


import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.Review;
import com.example.AffiliateAdda.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserAndProduct(User user, Product product); // Find existing review by user and product
    List<Review> findByProduct(Product product); // Find all reviews for a product

    List<Review> findByUser(User user);
}
