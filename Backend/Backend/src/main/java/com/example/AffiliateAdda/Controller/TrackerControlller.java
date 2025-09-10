package com.example.AffiliateAdda.Controller;

import com.example.AffiliateAdda.DTO.LinkDto;
import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.ProductRepository;
import com.example.AffiliateAdda.Repository.UserRepository;
import com.example.AffiliateAdda.Service.ActivationService;
import com.example.AffiliateAdda.Service.DeactivationService;
import com.example.AffiliateAdda.Service.ProductService;
import com.example.AffiliateAdda.Service.TrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/link")
public class TrackerController {

    @Autowired
    private TrackerService trackerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeactivationService deactivationService;

    @Autowired
    private ActivationService activationService;
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/generate")
    public ResponseEntity<String> generateLink(@RequestBody LinkDto linkDto, Principal principal) {

        System.out.println("inside generateLink - controller");

        //get user
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            if(!user.get().isActive()){
                return new ResponseEntity<>("User is not active", HttpStatus.FORBIDDEN);
            }
        }
        Long userId = user.map(User::getId).orElse(null);
        System.out.println("username " + username + " : id " + userId);

        //get Product
        Long productId = linkDto.getProductId();
        System.out.println("productId " + productId);
        Optional<Product> product = productService.findByProductId(productId);
        if (product.isEmpty()) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        if(!product.get().isActive()){
            return new ResponseEntity<>("Product is not active", HttpStatus.FORBIDDEN);
        }
        if(product.get().getUser().getId().equals(userId)) {
            return new ResponseEntity<>("Product owner can not generate the link", HttpStatus.FORBIDDEN);
        }

        // Get Baseurl
        String productBaseurl = product.get().getProductBaseurl();
        //System.out.println("productBaseurl " + productBaseurl);

        //encode url
        String encodedUrl = trackerService.generateLink(userId, productId, productBaseurl);
        System.out.println("Generated link: " + encodedUrl);

        return new ResponseEntity<>(encodedUrl, HttpStatus.OK);
    }

    // deactivate a tracker (generated link)
    @PostMapping("/deactivateTracker/{trackerId}")
    public ResponseEntity<String> deactivateTracker(@PathVariable Long trackerId) {
        //debug
        System.out.println("inside deactivateTracker - controller...................................................");
        try {
            deactivationService.deactivateTracker(trackerId, "Link deactivated");
            return new ResponseEntity<>("link deactivated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error deactivating link: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // activate a tracker (generated link)
    @PostMapping("/activateTracker/{trackerId}")
    public ResponseEntity<String> activateTracker(@PathVariable Long trackerId) {
        try {
            activationService.activateTracker(trackerId, "Link activated");
            return new ResponseEntity<>("link activated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error activating link: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/track")
    public ResponseEntity<String> trackClick(@RequestParam String data) {
        // Track the click and retrieve the product base URL after tracking
        String productBaseUrl = trackerService.trackClick(data);

        // If tracking fails, return a 400 Bad Request status
        if (productBaseUrl == null) {
            return new ResponseEntity<>("Failed to track click", HttpStatus.BAD_REQUEST);
        }

        // Return a success message with a 302 Found status for redirection
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", productBaseUrl);  // Redirect to the base URL

        return new ResponseEntity<>("Click tracked successfully!", headers, HttpStatus.FOUND);
    }

    @PostMapping("/track-buy")
    public ResponseEntity<String> trackBuy(@RequestParam String data) {
        return trackerService.trackBuy(data);
    }


}