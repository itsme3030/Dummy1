package com.example.AffiliateAdda.Controller;

import com.example.AffiliateAdda.DTO.ProfileResponseDTO;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.UserRepository;
import com.example.AffiliateAdda.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getUserProfile(Principal principal) {
        //get user
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        Long userId = user.map(User::getId).orElse(null);

        ProfileResponseDTO profileResponse = userService.getUserProfile(userId);

        return ResponseEntity.ok(profileResponse);
    }
}