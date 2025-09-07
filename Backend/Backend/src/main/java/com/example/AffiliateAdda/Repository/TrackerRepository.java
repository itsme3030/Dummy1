package com.example.AffiliateAdda.Repository;

import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.Tracker;
import com.example.AffiliateAdda.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackerRepository extends JpaRepository<Tracker, Long> {
    Tracker findByUserAndProduct(User user, Product product);

//    Custom query to sum the 'count' field for a given productId
//    @Query("SELECT SUM(t.count) FROM Tracker t WHERE t.product.productId = :productId")
//    long sumCountByProductId(Long productId);

    @Query("SELECT COALESCE(SUM(t.count), 0) FROM Tracker t WHERE t.product.productId = :productId")
    long sumCountByProductId(Long productId);

    //summing buyCount
    @Query("SELECT COALESCE(SUM(t.buyCount), 0) FROM Tracker t WHERE t.product.productId = :productId")
    long sumBuyCountByProductId(Long productId);


    List<Tracker> findByUserId(Long userId);

    List<Tracker> findByProduct(Product product);

    List<Tracker> findByUser(User user);

}
