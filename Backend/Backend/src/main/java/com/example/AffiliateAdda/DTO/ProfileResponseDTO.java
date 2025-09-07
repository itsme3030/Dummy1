package com.example.AffiliateAdda.DTO;

import com.example.AffiliateAdda.Model.TransactionStatus;
import com.example.AffiliateAdda.Model.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public class ProfileResponseDTO {
    private String username;
    private UserDetailDTO userDetail;
    private List<ProfileReviewDTO> reviews;

    private List<EarningDTO> earnings;
    private double totalEarnings;

    private List<PayableDTO> payableAmounts;
    private double totalPayableAmount;

    //payment
    private double totalWithdrawals;
    private double totalPays;
    private List<PaymentsDTO> payments;

    // Monthly based tracking
//    private List<MonthlyTrackerDTO> monthlyTrackers;
//
//
//    public List<MonthlyTrackerDTO> getMonthlyTrackers() {
//        return monthlyTrackers;
//    }
//
//    public void setMonthlyTrackers(List<MonthlyTrackerDTO> monthlyTrackers) {
//        this.monthlyTrackers = monthlyTrackers;
//    }

    public UserDetailDTO getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailDTO userDetail) {
        this.userDetail = userDetail;
    }

    public List<ProfileReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ProfileReviewDTO> reviews) {
        this.reviews = reviews;
    }


//    public List<Review> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(List<Review> reviews) {
//        this.reviews = reviews;
//    }

    public List<PaymentsDTO> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentsDTO> payments) {
        this.payments = payments;
    }

    public double getTotalWithdrawals() {
        return totalWithdrawals;
    }

    public void setTotalWithdrawals(double totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }

    public double getTotalPays() {
        return totalPays;
    }

    public void setTotalPays(double totalPays) {
        this.totalPays = totalPays;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<EarningDTO> getEarnings() {
        return earnings;
    }

    public void setEarnings(List<EarningDTO> earnings) {
        this.earnings = earnings;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public List<PayableDTO> getPayableAmounts() {
        return payableAmounts;
    }

    public void setPayableAmounts(List<PayableDTO> payableAmounts) {
        this.payableAmounts = payableAmounts;
    }

    public double getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(double totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public static class PaymentsDTO {
        private Long transactionId;
        private double amount;
        private TransactionType transactionType;
        private LocalDateTime transactionDate;
        private TransactionStatus status;

        // Getters and Setters


        public Long getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Long transactionId) {
            this.transactionId = transactionId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public TransactionType getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
        }

        public LocalDateTime getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(LocalDateTime transactionDate) {
            this.transactionDate = transactionDate;
        }

        public TransactionStatus getStatus() {
            return status;
        }

        public void setStatus(TransactionStatus status) {
            this.status = status;
        }
    }

    public static class EarningDTO {
        private Long tId;
        private Long productId;
        private String productGeneratedUrl;
        private String productName;
        private double perClickPrice;
        private long count;
        private double perBuyPrice;
        private long buyCount;
        private double commission = 0.5;
        private boolean active;
        private LocalDateTime createdAt;
        private List<MonthlyTrackerDTO> monthlyTrackers;

        // Getters and setters


        public double getCommission() {
            return commission;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public List<MonthlyTrackerDTO> getMonthlyTrackers() {
            return monthlyTrackers;
        }

        public void setMonthlyTrackers(List<MonthlyTrackerDTO> monthlyTrackers) {
            this.monthlyTrackers = monthlyTrackers;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getProductGeneratedUrl() {
            return productGeneratedUrl;
        }

        public void setProductGeneratedUrl(String productGeneratedUrl) {
            this.productGeneratedUrl = productGeneratedUrl;
        }

        public Long gettId() {
            return tId;
        }

        public void settId(Long tId) {
            this.tId = tId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPerClickPrice() {
            return perClickPrice;
        }

        public void setPerClickPrice(double perClickPrice) {
            this.perClickPrice = perClickPrice;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public double getPerBuyPrice() {
            return perBuyPrice;
        }

        public void setPerBuyPrice(double perBuyPrice) {
            this.perBuyPrice = perBuyPrice;
        }

        public long getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(long buyCount) {
            this.buyCount = buyCount;
        }
    }

    public static class PayableDTO {
        private Long productId;
        private String productBaseurl;
        private String productName;
        private double perClickPrice;
        private long count;
        private double perBuyPrice;
        private long buyCount;
        private boolean active;
        private LocalDateTime createdAt;
        private List<MonthlyTrackerDTO> monthlyTrackers;

        // Getters and setters


        public List<MonthlyTrackerDTO> getMonthlyTrackers() {
            return monthlyTrackers;
        }

        public void setMonthlyTrackers(List<MonthlyTrackerDTO> monthlyTrackers) {
            this.monthlyTrackers = monthlyTrackers;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getProductBaseurl() {
            return productBaseurl;
        }

        public void setProductBaseurl(String productBaseurl) {
            this.productBaseurl = productBaseurl;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPerClickPrice() {
            return perClickPrice;
        }

        public void setPerClickPrice(double perClickPrice) {
            this.perClickPrice = perClickPrice;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        public double getPerBuyPrice() {
            return perBuyPrice;
        }

        public void setPerBuyPrice(double perBuyPrice) {
            this.perBuyPrice = perBuyPrice;
        }

        public long getBuyCount() {
            return buyCount;
        }

        public void setBuyCount(long buyCount) {
            this.buyCount = buyCount;
        }
    }
}
