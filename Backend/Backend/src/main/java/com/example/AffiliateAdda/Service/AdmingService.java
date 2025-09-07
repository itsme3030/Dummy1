package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.DTO.AdminHomeResponseDTO;
import com.example.AffiliateAdda.DTO.ProfileResponseDTO;
import com.example.AffiliateAdda.Model.Transaction;
import com.example.AffiliateAdda.Model.TransactionStatus;
import com.example.AffiliateAdda.Model.TransactionType;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.TransactionRepository;
import com.example.AffiliateAdda.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    public AdminHomeResponseDTO getAdminHomeData() {
        // 1. Total number of users
        long totalUsers = userRepository.count();

        // 2. Get total earnings and payable amounts for all users
        double totalEarnings = 0;
        double totalPayableAmount = 0;

        // 3. Get the list of users along with their earnings and payable amounts
        List<User> users = userRepository.findAll();
//        List<UserHistory> userHistories = userHistoryRepository.findAll();
        List<AdminHomeResponseDTO.UserDataDTO> userList = new ArrayList<>();

        for (User user : users) {
            if(user.getRole().equals("ADMIN")) {
                totalUsers--;
                continue;
            }
            ProfileResponseDTO profile = userService.getUserProfile(user.getId());

            totalEarnings += profile.getTotalEarnings();
            totalPayableAmount += profile.getTotalPayableAmount();

            AdminHomeResponseDTO.UserDataDTO userData = new AdminHomeResponseDTO.UserDataDTO();
            userData.setUserId(user.getId());
            userData.setUsername(user.getUsername());
            userData.setTotalEarnings(profile.getTotalEarnings());
            userData.setTotalPayableAmount(profile.getTotalPayableAmount());
            userData.setActive(user.isActive());

            userList.add(userData);
        }

        // Total website earnings
        double websiteEarnings = 0L;
        List<Transaction> transactions = transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            if(transaction.getTransactionType() == TransactionType.PAYMENT && transaction.getStatus() == TransactionStatus.COMPLETED) {
                websiteEarnings += transaction.getAmount()/2; // 50% commission for each user
            }
        }

        // Create and return the response DTO
        AdminHomeResponseDTO response = new AdminHomeResponseDTO();
        response.setTotalUsers(totalUsers);
        response.setTotalEarnings(totalEarnings);
        response.setTotalPayableAmount(totalPayableAmount);
        response.setWebsiteEarnings(websiteEarnings);
        response.setUsers(userList);

        return response;
    }
}