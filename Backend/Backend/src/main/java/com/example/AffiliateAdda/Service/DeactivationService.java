package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.Tracker;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.ProductRepository;
import com.example.AffiliateAdda.Repository.TrackerRepository;
import com.example.AffiliateAdda.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeactivationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TrackerRepository trackerRepository;

    // Method to deactivate a tracker (generated link)
    public void deactivateTracker(Long trackerId, String reason) {
        //debug
        System.out.println("deactivating tracker - DeactivationService.............. " + trackerId);

        Tracker tracker = trackerRepository.findById(trackerId).orElseThrow(() -> new RuntimeException("Tracker not found"));

        tracker.setActive(false);
        trackerRepository.save(tracker);

    }

    // Method to deactivate a product
    public void deactivateProduct(Long productId, String reason) {

        //debug
        System.out.println("deactivating Product - DeactivationService.............. " + productId);

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        product.setActive(false);
        productRepository.save(product);

        // Archive associated trackers (generated links for this product)
        List<Tracker> trackers = trackerRepository.findByProduct(product);  // Fetch all trackers associated with the product
        for (Tracker tracker : trackers) {
            deactivateTracker(tracker.getTId(), reason);
        }

//        // Delete product (soft delete)
//        productRepository.delete(product);
    }

    // Method to deactivate a user
    public void deactivateUser(Long userId, String reason) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        userRepository.save(user);

        // Archive associated trackers
        List<Tracker> trackers = trackerRepository.findByUser(user);
        for (Tracker tracker : trackers) {
            deactivateTracker(tracker.getTId(), reason);
        }

        // Archive associated products (if user uploaded products)
        List<Product> products = productRepository.findByUser(user);
        for (Product product : products) {
            deactivateProduct(product.getProductId(),reason);
        }

//        // Mark user as deleted (soft delete)
//        userRepository.delete(user);
    }

}