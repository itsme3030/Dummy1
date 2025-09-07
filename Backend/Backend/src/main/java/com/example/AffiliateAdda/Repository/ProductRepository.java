package com.example.AffiliateAdda.Repository;

import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductId(Long product_id);
    Optional<Product> findByProductName(String productName);
    List<Product> findByUserId(Long userId);
    List<Product> findByUser(User user);
}
