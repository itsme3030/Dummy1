package com.example.AffiliateAdda.DTO;

import java.util.List;

public class AdminHomeResponseDTO {

    private long totalUsers;
    private double totalEarnings;
    private double totalPayableAmount;
    private  double websiteEarnings;
    private List<UserDataDTO> users;

    // Getters and setters


    public double getWebsiteEarnings() {
        return websiteEarnings;
    }

    public void setWebsiteEarnings(double websiteEarnings) {
        this.websiteEarnings = websiteEarnings;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public double getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(double totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public List<UserDataDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDataDTO> users) {
        this.users = users;
    }

    public static class UserDataDTO {
        private long userId;
        private String username;
        private double totalEarnings;
        private double totalPayableAmount;
        private boolean active;

        // Getters and setters


        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public double getTotalEarnings() {
            return totalEarnings;
        }

        public void setTotalEarnings(double totalEarnings) {
            this.totalEarnings = totalEarnings;
        }

        public double getTotalPayableAmount() {
            return totalPayableAmount;
        }

        public void setTotalPayableAmount(double totalPayableAmount) {
            this.totalPayableAmount = totalPayableAmount;
        }
    }
}
