package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.Model.*;
import com.example.AffiliateAdda.Repository.*;
import com.google.api.client.util.Value;
import jakarta.persistence.criteria.Order;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackerRepository trackerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserDetailRepository userDetailRepository;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    public String processPayment(Principal principal, double amount) {
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return null;
        }

        //debug
        System.out.println("User " + username);

        // We will use it for per month pay : Mail to user for remaining payment...
//        double userPayableAmount = findUserPayableAmount(user.get().getId());
//        double userPays = findUserPays(user.get());
//        double remainingPays = userPayableAmount - userPays;

        // Create a transaction record
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setUser(user.get());
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setTransactionDate(LocalDateTime.now());

        //RazorPay
        String orderId;
        try{
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount",amount*100); // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
            orderRequest.put("currency","INR");
            orderRequest.put("receipt", "tnx"); // Will pass proper receipt in the future.

            Order order = razorpay.orders.create(orderRequest);
            System.out.println("Order created");

            //OrderID
            orderId = order.get("id");
            transaction.setOrderId(orderId);
            transaction.setStatus(TransactionStatus.PENDING);
            transactionRepository.save(transaction);

        } catch (RazorpayException e) {
            // Log the specific error from Razorpay
            System.err.println("Error from Razorpay API: " + e.getMessage());
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new RuntimeException(e);
        }

        //PaymentID - done
        //transaction.setStatus(TransactionStatus.COMPLETED);
        //transactionRepository.save(transaction);

        //debug
        System.out.println("Transaction " + transaction.getTransactionId());

        return orderId;
    }

    public String updatePayment(Principal principal, String orderId, String paymentId) {
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return null;
        }

        Transaction transaction = transactionRepository.findByOrderId(orderId);
        transaction.setPaymentId(paymentId);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        return "Payment Successful";
    }

    public boolean processWithdrawal(Principal principal, double amount) {
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return false;
        }

        boolean success = false;

        double userEarnings = findUserEarnings(user.get().getId());
        System.out.println("UserEarnings " + userEarnings);
        double userWithdrawals = findUserWithdrawals(user.get());
        System.out.println("UserWithdrawals " + userWithdrawals);
        double remainingEarnings = userEarnings - userWithdrawals;
        System.out.println("Remaining Earnings " + remainingEarnings);
        if(remainingEarnings < amount){
            return false;
        }

        // Create a transaction record
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setUser(user.get());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        //update detail in userprofile section
        UserDetail userDetail = user.get().getUserDetail();
        if(userDetail == null){
            userDetail = new UserDetail();
            userDetail.setUser(user.get());
            userDetail.setTotalWithdrawal(amount);
            userDetailRepository.save(userDetail);
        }else{
            userDetail.setTotalWithdrawal(userDetail.getTotalWithdrawal() + amount);
            userDetailRepository.save(userDetail);
        }

        success = true;
        return success;
    }

    public double findUserEarnings(Long userId) {
        List<Tracker> trackers = trackerRepository.findByUserId(userId);

        double totalEarnings = 0;
        double commission = 0.5;

        for (Tracker tracker : trackers) {
            Product product = tracker.getProduct();
            long count = tracker.getCount();
            long buyCount = tracker.getBuyCount();

            double earningForProduct = product.getPerClickPrice() * commission * count;
            double earningForProductBuy = product.getPerBuyPrice() * commission * buyCount;

            totalEarnings += earningForProduct + earningForProductBuy;
        }
        return totalEarnings;
    }

    private double findUserWithdrawals(User user) {
        List<Transaction> transactions = transactionRepository.findByUser(user);
        double totalWithdrawals = 0;
        for (Transaction transaction : transactions) {
            if(transaction.getTransactionType() == TransactionType.WITHDRAWAL && transaction.getStatus() == TransactionStatus.COMPLETED){
                totalWithdrawals += transaction.getAmount();
            }
        }
        return totalWithdrawals;
    }

    public double findUserPayableAmount(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        double totalPayableAmount = 0;

        // Fetch products uploaded by the user
        List<Product> products = productRepository.findByUserId(userId);
        for (Product product : products) {

            // Count the number of clicks for each product based on trackers
            long totalCountForProduct = trackerRepository.sumCountByProductId(product.getProductId());
            long totalCountForProductBuy = trackerRepository.sumBuyCountByProductId(product.getProductId());

            double payableForProduct = product.getPerClickPrice() * totalCountForProduct +
                    product.getPerBuyPrice() * totalCountForProductBuy;

            totalPayableAmount += payableForProduct;
        }
        return totalPayableAmount;
    }

    private double findUserPays(User user) {
        List<Transaction> transactions = transactionRepository.findByUser(user);
        double totalPays = 0;
        for (Transaction transaction : transactions) {
            if(transaction.getTransactionType() == TransactionType.PAYMENT && transaction.getStatus() == TransactionStatus.COMPLETED){
                totalPays += transaction.getAmount();
            }
        }
        return totalPays;
    }
}