package com.example.AffiliateAdda.Controller;

import com.example.AffiliateAdda.DTO.AdminHomeResponseDTO;
import com.example.AffiliateAdda.Service.ActivationService;
import com.example.AffiliateAdda.Service.AdminService;
import com.example.AffiliateAdda.Service.DeactivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private DeactivationService deactivationService;

    @Autowired
    private ActivationService activationService;

    @GetMapping("/home")
    public AdminHomeResponseDTO getAdminHomeData() {
        return adminService.getAdminHomeData();
    }

    // deactivate a user
    @PostMapping("/deactivateUser/{userId}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId) {
        try {
            deactivationService.deactivateUser(userId, "User deactivated");
            return new ResponseEntity<>("User deactivated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error deactivating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // deactivate a user
    @PostMapping("/activateUser/{userId}")
    public ResponseEntity<String> activateUser(@PathVariable Long userId) {
        try {
            activationService.activateUser(userId, "User activated");
            return new ResponseEntity<>("User activated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error activating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
