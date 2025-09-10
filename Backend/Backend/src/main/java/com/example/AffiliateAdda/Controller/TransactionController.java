package com.example.AffiliateAdda.Controller;

import com.example.AffiliateAdda.DTO.TransactionDTO;
import com.example.AffiliateAdda.Repository.UserRepository;
import com.example.AffiliateAdda.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    // Payment Endpoint
    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(Principal principal, @RequestBody TransactionDTO transactionDTO) {

        //debug
        System.out.println("----------------------------------------->inside processPayment : Pay : " + transactionDTO.getAmount());
        try {
            String orderId = transactionService.processPayment(principal, transactionDTO.getAmount());
            if (orderId != null) {
                return ResponseEntity.ok(orderId);
            } else {
                return ResponseEntity.status(400).body("Payment Failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/updatePay")
    public ResponseEntity<String> updatePayment(Principal principal, @RequestBody TransactionDTO transactionDTO) {
        try {
            String msg = transactionService.updatePayment(principal, transactionDTO.getOrderId(), transactionDTO.getPaymentId());
            if (msg != null) {
                return ResponseEntity.ok(msg);
            }else {
                return ResponseEntity.status(400).body("Payment Failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // Endpoint for withdrawal transactions
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawFunds(Principal principal, @RequestBody TransactionDTO transactionDTO) {
        try {
            boolean withdrawalSuccess = transactionService.processWithdrawal(principal, transactionDTO.getAmount());
            if (withdrawalSuccess) {
                return ResponseEntity.ok("Withdrawal Successful");
            } else {
                return ResponseEntity.status(400).body("Withdrawal Failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}