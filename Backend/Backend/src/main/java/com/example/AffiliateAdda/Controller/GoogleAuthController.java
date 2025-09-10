package com.example.AffiliateAdda.Controller;

import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;

import com.google.api.client.json.gson.GsonFactory;

import com.google.api.client.http.javanet.NetHttpTransport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
@RestController
@RequestMapping("/auth")
public class GoogleAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody TokenRequest tokenRequest) {
        String idTokenString = tokenRequest.getToken();
        try {
            NetHttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            // Create GoogleIdTokenVerifier
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(clientId))
                    .build();

            // Verify the token
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Extract user details from token
                String email = payload.getEmail();
                String username = payload.getSubject();
                System.out.println("email: " + email);
                System.out.println("username: " + username);
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");

                // Check if the email is verified
                if (!emailVerified) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not verified.");
                }

                // Check if the user exists in the database
                User user = userRepository.findByUsername(email).orElse(null);
                if (user == null) {
                    // Create a new user if not found
                    User newUser = new User();
                    newUser.setUsername(email);
                    newUser.setPassword(new BCryptPasswordEncoder().encode(generateRandomPassword()));
                    userRepository.save(newUser);
                    user = newUser;
                }else{
                    // Existing user
                    // Check if the user is active or not
                    if(!user.isActive()){
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not active");
                    }
                }

                // Generate a JWT token for the user
                String role="USER";
                if(user.getUsername().equals("yash2.try2@gmail.com")) {
                    role="ADMIN";
                }
                String jwtToken = jwtUtil.generateToken(user.getUsername(),role);
                return ResponseEntity.ok(new AuthResponse(jwtToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Google login failed.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session
        request.getSession().invalidate();

        // Clear SecurityContext
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("User logged out successfully.");
    }

    private String generateRandomPassword() {
        return "Random@123"; // Replace with your password generation logic
    }
}

class TokenRequest {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

@Data
@NoArgsConstructor
class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}