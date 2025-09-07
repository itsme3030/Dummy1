package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.DTO.MonthlyTrackerDTO;
import com.example.AffiliateAdda.DTO.ProfileResponseDTO;
import com.example.AffiliateAdda.DTO.ProfileReviewDTO;
import com.example.AffiliateAdda.DTO.UserDetailDTO;
import com.example.AffiliateAdda.Model.*;
import com.example.AffiliateAdda.Repository.*;
import org.checkerframework.checker.lock.qual.EnsuresLockHeldIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TrackerRepository trackerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MonthlyTrackerRepository monthlyTrackerRepository;

//    public double findUserEarnings(Long userId) {
//        List<Tracker> trackers = trackerRepository.findByUserId(userId);
//
//        double totalEarnings = 0;
//        double commission = 0.5;
//
//        for (Tracker tracker : trackers) {
//            Product product = tracker.getProduct();
//            long count = tracker.getCount();
//            long buyCount = tracker.getBuyCount();
//
//            double earningForProduct = product.getPerClickPrice() * commission * count;
//            double earningForProductBuy = product.getPerBuyPrice() * commission * buyCount;
//
//            totalEarnings += earningForProduct + earningForProductBuy;
//        }
//        return new totalEarnings;
//    }

//    public double findUserPayableAmount(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        double totalPayableAmount = 0;
//
//        // Fetch products uploaded by the user
//        List<Product> products = productRepository.findByUserId(userId);
//        for (Product product : products) {
//
//            // Count the number of clicks for each product based on trackers
//            long totalCountForProduct = trackerRepository.sumCountByProductId(product.getProductId());
//            long totalCountForProductBuy = trackerRepository.sumBuyCountByProductId(product.getProductId());
//
//            double payableForProduct = product.getPerClickPrice() * totalCountForProduct +
//                    product.getPerBuyPrice() * totalCountForProductBuy;
//
//            totalPayableAmount += payableForProduct;
//        }
//        return totalPayableAmount;
//    }

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public ProfileResponseDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        //EARN
        // Fetch all trackers for the user
        List<Tracker> trackers = trackerRepository.findByUserId(userId);

        //List<TrackerHistory> trackerHistories = trackerHistoryRepository.findByUserId(userId);

        // Calculate earnings (for the user who is sharing the link)
        EnsuresLockHeldIf.List<ProfileResponseDTO.EarningDTO> earnings = new ArrayList<>();
        double totalEarnings = 0;
        double commission = 0.5;

        // Loop through trackers directly to compute earnings
        for (Tracker tracker : trackers) {
            Product product = tracker.getProduct();
            long count = tracker.getCount();
            long buyCount = tracker.getBuyCount();
            long tId = tracker.getTId();
            String productGeneratedUrl = tracker.getProductGeneratedUrl();

            double earningForProduct = product.getPerClickPrice() * commission * count;
            double earningForProductBuy = product.getPerBuyPrice() * commission * buyCount;

            ProfileResponseDTO.EarningDTO earningDTO = new ProfileResponseDTO.EarningDTO();
            earningDTO.settId(tId);
            earningDTO.setProductId(product.getProductId());
            earningDTO.setProductGeneratedUrl(productGeneratedUrl);
            earningDTO.setProductName(product.getProductName());
            earningDTO.setPerClickPrice(product.getPerClickPrice() * commission);
            earningDTO.setCount(count);
            earningDTO.setPerBuyPrice(product.getPerBuyPrice() * commission);
            earningDTO.setBuyCount(buyCount);
            earningDTO.setActive(tracker.isActive());
            earningDTO.setCreatedAt(tracker.getCreatedAt());

            // Monthly tracker
            List<MonthlyTrackerDTO> monthlyTrackerDTOs = new ArrayList<>();
            List<MonthlyTracker> monthlyTrackers = tracker.getMonthlyTrackers();
            for (MonthlyTracker monthlyTracker : monthlyTrackers) {
                MonthlyTrackerDTO monthlyTrackerDTO = new MonthlyTrackerDTO();
                monthlyTrackerDTO.setMonthlyTrackerId(monthlyTracker.getMonthlyTrackerId());
                monthlyTrackerDTO.settId(tracker.getTId());
                monthlyTrackerDTO.setCount(monthlyTracker.getCount());
                monthlyTrackerDTO.setBuyCount(monthlyTracker.getBuyCount());
                monthlyTrackerDTO.setMonth(YearMonth.parse(monthlyTracker.getMonth())); //YearMonth.parse(monthlyTracker.getMonth())
                monthlyTrackerDTOs.add(monthlyTrackerDTO);
            }
            earningDTO.setMonthlyTrackers(monthlyTrackerDTOs);

            earnings.add(earningDTO);
            totalEarnings += earningForProduct + earningForProductBuy;
        }

//        // Loop through trackerHistories directly to compute earnings
//        for (TrackerHistory trackerHistory : trackerHistories) {
//
//            long count = trackerHistory.getCount();
//            long buyCount = trackerHistory.getBuyCount();
//            long tId = trackerHistory.gettId();
//            String productGeneratedUrl = trackerHistory.getProductGeneratedUrl();
//            double earningForProduct;
//            double earningForProductBuy;
//
//            Long productId = trackerHistory.getProductId();
//            Product product = null;
//            ProductHistory productHistory = null;
//
//            product = productRepository.findById(productId).orElse(null);
//            if (product != null) {
//                earningForProduct = product.getPerClickPrice() * commission * count;
//                earningForProductBuy = product.getPerBuyPrice() * commission * buyCount;
//            }else{
//                productHistory = productHistoryRepository.findById(productId).orElse(null);
//                if (productHistory != null) {
//                    earningForProduct = productHistory.getPerClickPrice() * commission * count;
//                    earningForProductBuy = productHistory.getPerBuyPrice() * commission * buyCount;
//                }else{
//                    throw new RuntimeException("Product & Product history not found");
//                }
//            }
//
//            ProfileResponseDTO.EarningDTO earningDTO = new ProfileResponseDTO.EarningDTO();
//            earningDTO.settId(tId);
//            earningDTO.setProductGeneratedUrl(productGeneratedUrl);
//            earningDTO.setCount(count);
//            earningDTO.setBuyCount(buyCount);
//            earningDTO.setActive(true);
//            earningDTO.setProductName(product!=null?product.getProductName():productHistory.getProductName());
//            earningDTO.setPerClickPrice(product!=null?product.getPerClickPrice():productHistory.getPerClickPrice());
//            earningDTO.setPerBuyPrice(product!=null?product.getPerBuyPrice():productHistory.getPerBuyPrice());
//            earningDTO.setActive(false);
//
//            earnings.add(earningDTO);
//            totalEarnings += earningForProduct + earningForProductBuy;
//        }


        //PAY
        // Calculate payable amounts (for the user who uploaded the product)
        List<ProfileResponseDTO.PayableDTO> payableAmounts = new ArrayList<>();
        double totalPayableAmount = 0;

        // Fetch products uploaded by the user
        List<Product> products = productRepository.findByUserId(userId);
        for (Product product : products) {
            // Count the number of clicks for each product based on trackers

            long productId = product.getProductId();
            String productBaseurl = product.getProductBaseurl();
            long totalCountForProduct = trackerRepository.sumCountByProductId(product.getProductId());
            long totalCountForProductBuy = trackerRepository.sumBuyCountByProductId(product.getProductId());

            double payableForProduct = product.getPerClickPrice() * totalCountForProduct +
                    product.getPerBuyPrice() * totalCountForProductBuy;

            // Monthly trackerDTOs
            List<Tracker> trackers1 = trackerRepository.findByProduct(product);
            List<MonthlyTrackerDTO> monthlyTrackerDTOs = new ArrayList<>();
            for (Tracker tracker : trackers1) {
                // Monthly tracker
                List<MonthlyTracker> monthlyTrackers = tracker.getMonthlyTrackers();
                for (MonthlyTracker monthlyTracker : monthlyTrackers) {
                    MonthlyTrackerDTO monthlyTrackerDTO = new MonthlyTrackerDTO();
                    monthlyTrackerDTO.setMonthlyTrackerId(monthlyTracker.getMonthlyTrackerId());
                    monthlyTrackerDTO.settId(tracker.getTId());
                    monthlyTrackerDTO.setCount(monthlyTracker.getCount());
                    monthlyTrackerDTO.setBuyCount(monthlyTracker.getBuyCount());
                    monthlyTrackerDTO.setMonth(YearMonth.parse(monthlyTracker.getMonth()));

                    monthlyTrackerDTOs.add(monthlyTrackerDTO);
                }
            }

            ProfileResponseDTO.PayableDTO payableDTO = new ProfileResponseDTO.PayableDTO();
            payableDTO.setProductId(productId);
            payableDTO.setProductBaseurl(productBaseurl);
            payableDTO.setProductName(product.getProductName());
            payableDTO.setPerClickPrice(product.getPerClickPrice());
            payableDTO.setCount(totalCountForProduct);
            payableDTO.setPerBuyPrice(product.getPerBuyPrice());
            payableDTO.setBuyCount(totalCountForProductBuy);
            payableDTO.setActive(product.isActive());
            payableDTO.setCreatedAt(product.getCreatedAt());
            payableDTO.setMonthlyTrackers(monthlyTrackerDTOs);

            payableAmounts.add(payableDTO);
            totalPayableAmount += payableForProduct;
        }

//        // Fetch productsHistory
//        List<ProductHistory> productHistories = productHistoryRepository.findByUserId(userId);
//        for (ProductHistory productHistory : productHistories) {
//            long productId = productHistory.getProductId();
//            String productBaseurl = productHistory.getProductBaseurl();
//            long totalCountForProduct = trackerHistoryRepository.sumCountByProductId(productHistory.getProductId());
//            long totalCountForProductBuy = trackerHistoryRepository.sumBuyCountByProductId(productHistory.getProductId());
//
//            double payableForProduct = productHistory.getPerClickPrice() * totalCountForProduct +
//                    productHistory.getPerBuyPrice() * totalCountForProductBuy;
//
//            ProfileResponseDTO.PayableDTO payableDTO = new ProfileResponseDTO.PayableDTO();
//            payableDTO.setProductId(productId);
//            payableDTO.setProductBaseurl(productBaseurl);
//            payableDTO.setProductName(productHistory.getProductName());
//            payableDTO.setPerClickPrice(productHistory.getPerClickPrice());
//            payableDTO.setCount(totalCountForProduct);
//            payableDTO.setPerBuyPrice(productHistory.getPerBuyPrice());
//            payableDTO.setBuyCount(totalCountForProductBuy);
//            payableDTO.setActive(false);
//
//            payableAmounts.add(payableDTO);
//            totalPayableAmount += payableForProduct;
//        }

        // Total Payments and Total Withdrawals
        List<ProfileResponseDTO.PaymentsDTO> payments = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findByUser(user);
        double totalPays = 0L;
        double totalWithdrawals = 0L;
        for (Transaction transaction : transactions) {
            if(transaction.getTransactionType() == TransactionType.PAYMENT || transaction.getTransactionType() == TransactionType.WITHDRAWAL){

                // PaymentsDTO
                ProfileResponseDTO.PaymentsDTO paymentsDTO = new ProfileResponseDTO.PaymentsDTO();
                paymentsDTO.setTransactionId(transaction.getTransactionId());
                paymentsDTO.setAmount(transaction.getAmount());
                paymentsDTO.setTransactionType(transaction.getTransactionType());
                paymentsDTO.setStatus(transaction.getStatus());
                paymentsDTO.setTransactionDate(transaction.getTransactionDate());

                payments.add(paymentsDTO);
                if(transaction.getTransactionType() == TransactionType.PAYMENT && transaction.getStatus() == TransactionStatus.COMPLETED){
                    totalPays += transaction.getAmount();
                }else if(transaction.getTransactionType() == TransactionType.WITHDRAWAL && transaction.getStatus() == TransactionStatus.COMPLETED){
                    totalWithdrawals += transaction.getAmount();
                }
            }
        }

//        System.out.println("Total withdrawals: --------------> " + totalWithdrawals);
//        System.out.println("Total payments: ---------------> " + totalPays);

        // Review
        List<Review> reviews = reviewRepository.findByUser(user);
        List<ProfileReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review : reviews) {
            ProfileReviewDTO profileReviewDTO = new ProfileReviewDTO();
            profileReviewDTO.setReviewId(review.getReviewId());
            profileReviewDTO.setReviewDate(review.getReviewDate());
            profileReviewDTO.setRating(review.getRating());
            profileReviewDTO.setReviewText(review.getReviewText());
            profileReviewDTO.setProductName(review.getProduct().getProductName());
            reviewDTOs.add(profileReviewDTO);
        }

        // UserProfile
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        if(user.getUserDetail() != null){
            userDetailDTO.setUserDetailId(user.getUserDetail().getUserDetailId());
            userDetailDTO.setFirstName(user.getUserDetail().getFirstName());
            userDetailDTO.setLastName(user.getUserDetail().getLastName());
            userDetailDTO.setEmail(user.getUserDetail().getEmail());
            userDetailDTO.setPhone(user.getUserDetail().getPhone());
            userDetailDTO.setAddress(user.getUserDetail().getAddress());
            userDetailDTO.setCity(user.getUserDetail().getCity());
            userDetailDTO.setState(user.getUserDetail().getState());
            userDetailDTO.setCountry(user.getUserDetail().getCountry());
            userDetailDTO.setZip(user.getUserDetail().getZip());
        }


        // Monthly Tracker : 1 user - * tracker , 1 tracker - * monthly tracker


        // Build the response DTO
        ProfileResponseDTO profileResponseDTO = new ProfileResponseDTO();
        profileResponseDTO.setUsername(user.getUsername());
        profileResponseDTO.setEarnings(earnings);
        profileResponseDTO.setTotalEarnings(totalEarnings);
        profileResponseDTO.setPayableAmounts(payableAmounts);
        profileResponseDTO.setTotalPayableAmount(totalPayableAmount);
        profileResponseDTO.setTotalPays(totalPays);
        profileResponseDTO.setTotalWithdrawals(totalWithdrawals);
        profileResponseDTO.setPayments(payments);
        profileResponseDTO.setReviews(reviewDTOs);
        profileResponseDTO.setUserDetail(userDetailDTO);

        return profileResponseDTO;
    }

}