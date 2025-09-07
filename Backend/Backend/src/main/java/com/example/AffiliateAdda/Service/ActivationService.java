package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.Tracker;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.ProductRepository;
import com.example.AffiliateAdda.Repository.TrackerRepository;
import com.example.AffiliateAdda.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ActivationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TrackerRepository trackerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void activateTracker(Long trackerId, String reason) {
        Tracker tracker = trackerRepository.findById(trackerId).orElseThrow(() -> new RuntimeException("Tracker not found"));
        User user = userRepository.findById(tracker.getUser().getId()).orElse(null);
        if (user == null) {
            //System.out.println("------------------------------------------------------------------>User not found");
            throw new RuntimeException("User not found");
        }
        if(!user.isActive()){
            throw new RuntimeException("User is not active");
        }

        Product product = productRepository.findById(tracker.getProduct().getProductId()).orElse(null);
        if (product == null) {
            //System.out.println("------------------------------------------------------------------>User not found");
            throw new RuntimeException("Product not found");
        }
        if(!product.isActive()){
            throw new RuntimeException("Product is not active");
        }

        tracker.setActive(true);
        trackerRepository.save(tracker);
    }

    public void activateProduct(Long productId, String reason) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        User user = userRepository.findById(product.getUser().getId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if(!user.isActive()){
            throw new RuntimeException("User is not active");
        }
        product.setActive(true);
        productRepository.save(product);

        List<Tracker> trackers = trackerRepository.findByProduct(product);
        for (Tracker tracker : trackers) {
            User user1 = userRepository.findById(tracker.getUser().getId()).orElse(null);
            if (user1 == null || !user1.isActive()) {
                continue;
            }
            tracker.setActive(true);
            trackerRepository.save(tracker);
        }

    }

    public void activateUser(Long userId, String reason) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(true);
        userRepository.save(user);

        List<Tracker> trackers = trackerRepository.findByUser(user);
        for (Tracker tracker : trackers) {
            Product product = productRepository.findById(tracker.getProduct().getProductId()).orElse(null);
            if (product == null || !product.isActive()) {
                continue;
            }
            tracker.setActive(true);
            trackerRepository.save(tracker);
        }

        List<Product> products = productRepository.findByUser(user);
        for (Product product : products) {
            activateProduct(product.getProductId(), reason);
        }
    }
}
