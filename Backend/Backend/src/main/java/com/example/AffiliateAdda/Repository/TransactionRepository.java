package com.example.AffiliateAdda.Repository;

import com.example.AffiliateAdda.Model.Transaction;
import com.example.AffiliateAdda.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    Transaction findByOrderId(String orderId);
}