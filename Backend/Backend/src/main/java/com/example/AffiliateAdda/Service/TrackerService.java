package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.Model.MonthlyTracker;
import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.Tracker;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.MonthlyTrackerRepository;
import com.example.AffiliateAdda.Repository.ProductRepository;
import com.example.AffiliateAdda.Repository.TrackerRepository;
import com.google.api.client.util.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Base64;
import java.util.Optional;

@Service
public class TrackerService {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${dummy.frontend.url}")
    private String dummyFrontendUrl;

    @Value("${Domain}")
    private String domain;

    @Autowired
    private TrackerRepository trackerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MonthlyTrackerRepository monthlyTrackerRepository;


    public String generateLink(Long userId, Long productId, String productBaseurl) {

        // Fetch the User and Product entities using the IDs
        Optional<User> user = userService.findByUserId(userId);
        Optional<Product> product = productService.findByProductId(productId);

        // Check if either user or product is missing
        if (!user.isPresent()) {
            return "User not found";
        }
        if (!product.isPresent()) {
            return "Product not found";
        }

        // Find the LinkTrackerTable entry based on the user and product associations
        Optional<Tracker> linkTracker = Optional.ofNullable(trackerRepository.findByUserAndProduct(user.get(), product.get()));
        if(linkTracker.isPresent()) {
            if(!linkTracker.get().isActive()){
                return "Product not active";
            }
            Tracker tracker = linkTracker.get();
            return tracker.getProductGeneratedUrl();
        }

        // Construct the query string with the parameters
        String queryString = String.format("user_id=%d&product_id=%d&product_baseurl=%s", userId, productId, productBaseurl);

        // Encode only the query parameters part
        String encodedParams = Base64.getEncoder().encodeToString(queryString.getBytes());

        // Construct the base URL with the encoded query parameters
        String mydomainurl = domain+"/link/track";
        String url = String.format("%s?data=%s", mydomainurl, encodedParams);

        // Check if user and product exist
        if (!user.isPresent() || !product.isPresent()) {
            return "User or Product not found";
        }

        Tracker tracker = new Tracker();
        tracker.setProductGeneratedUrl(url); // Save the full URL with encoded query params

        // Set the user and product associations
        tracker.setUser(user.get());
        tracker.setProduct(product.get());
        tracker.setCreatedAt(LocalDateTime.now());
        // Save or update the record in the database
        trackerRepository.save(tracker);

        return url; // Returning the full URL with the encoded query parameters
    }


    public String trackClick(String encodedUrl) {
        // Decode the parameters
        String decodedParams = new String(Base64.getDecoder().decode(encodedUrl));

        // Split the decoded parameters by "&"
        String[] params = decodedParams.split("&");

        // Extract the individual parameters
        Long userId = Long.valueOf(params[0].split("=")[1]);
        Long productId = Long.valueOf(params[1].split("=")[1]);
        String productBaseUrl = params[2].split("=")[1];  // Extract product base URL

        // Fetch the User and Product entities
        Optional<User> user = userService.findByUserId(userId);
        Optional<Product> product = productService.findByProductId(productId);

        // Check if user and product exist
        if (user.isEmpty() || product.isEmpty()) {
            return null; // Return null if user or product not found
        }
        // Check if user and product is inactive
        if(!user.get().isActive() || !product.get().isActive()) {
            return null;
        }
        // Check if product clickCount or BuyCount > 0
//        if(product.get().getClickCount()<=0 || product.get().getBuyCount()<=0){
//            return null;
//        }

        // Find the LinkTrackerTable entry based on the user and product associations
        Optional<Tracker> linkTracker = Optional.ofNullable(trackerRepository.findByUserAndProduct(user.get(), product.get()));

        if (linkTracker.isPresent()) {
            Tracker tracker = linkTracker.get();
            if(!tracker.isActive()){
                return "Tracker not active";
            }
//            if(product.get().getBuyCount()==1){
//                product.get().setActive(false);
//            }
//            product.get().setClickCount(product.get().getClickCount()-1); // Decrement click count of product
//            productRepository.save(product.get()); // Save the updated product
            tracker.setCount(tracker.getCount() + 1); // Increment click count
            trackerRepository.save(tracker); // Save the updated tracker

            // Get the current month
            YearMonth currentMonth = YearMonth.now();

            // Find or create the MonthlyTracker record for the current month
            Optional<MonthlyTracker> monthlyTrackerOptional = monthlyTrackerRepository.findByTrackerAndMonth(tracker, currentMonth.toString());

            if (monthlyTrackerOptional.isPresent()) {
                MonthlyTracker monthlyTracker = monthlyTrackerOptional.get();
                // Increment the count for the current month
                monthlyTracker.setCount(monthlyTracker.getCount() + 1);
                monthlyTrackerRepository.save(monthlyTracker);
            } else {
                // If no entry exists for this month, create a new one
                MonthlyTracker monthlyTracker = new MonthlyTracker();
                monthlyTracker.setTracker(tracker);
                monthlyTracker.setMonth(currentMonth.toString());
                monthlyTracker.setCount(1L);  // First click of the month
                monthlyTrackerRepository.save(monthlyTracker);
            }
        }

        //debug
        System.out.println("productBaseUrl: " + productBaseUrl);
        // Check if the base URL is of Dummy server
        if (productBaseUrl.trim().toLowerCase().startsWith(dummyFrontendUrl+"/product")) {
            System.out.println("Dummy server Product--------------->"+productBaseUrl);
            System.out.println("Adding userId : "+userId+"\nAdding productId : "+productId);
            // Append userId and productId as query parameters
            productBaseUrl = productBaseUrl + "?data=" + encodeParams(userId, productId);
        }

        // Return the product base URL if click tracking was successful
        return productBaseUrl;
    }

    public String encodeParams(Long userId, Long productId) {
        String combinedParams = userId + ":" + productId; // Concatenate userId and productId
        return Base64.getEncoder().encodeToString(combinedParams.getBytes()); // Base64 encode
    }


    public ResponseEntity<String> trackBuy(String data) {
        try {
            //debug
            System.out.println("inside trackBuy - service : "+data);
            // Decode the encoded data (Base64)
            String decodedData = new String(Base64.getDecoder().decode(data));

            // Split the decoded data by "&" to separate the individual parameters
            String[] params = decodedData.split("&");

            // Extract userId, productId, and buyCount
            Long userId = Long.valueOf(params[0].split("=")[1]);
            Long productId = Long.valueOf(params[1].split("=")[1]);
            Long buyCount = Long.valueOf(params[2].split("=")[1]);

            // Fetch the User and Product entities
            Optional<User> user = userService.findByUserId(userId);
            Optional<Product> product = productService.findByProductId(productId);

            if (user.isEmpty() || product.isEmpty()) {
                return new ResponseEntity<>("User or Product not found", HttpStatus.NOT_FOUND);
            }
            if(!user.get().isActive() || !product.get().isActive()) {
                return new ResponseEntity<>("User or Product not active", HttpStatus.FORBIDDEN);
            }
            // Check if product clickCount or BuyCount > 0
//            if(product.get().getBuyCount()<=0){
//                return null;
//            }

            // Find the LinkTrackerTable entry based on the user and product associations
            Optional<Tracker> linkTracker = Optional.ofNullable(trackerRepository.findByUserAndProduct(user.get(), product.get()));


            if (linkTracker.isPresent()) {
                Tracker tracker = linkTracker.get();
                if(!tracker.isActive()){
                    return new ResponseEntity<>("Tracker not active", HttpStatus.NOT_FOUND);
                }
//                if(product.get().getBuyCount()<=buyCount){
//                    buyCount = product.get().getBuyCount();
//                    product.get().setActive(false);
//                }
//                product.get().setBuyCount(product.get().getBuyCount()-buyCount); // Decrement the buy count of product
//                productRepository.save(product.get());
                tracker.setBuyCount(tracker.getBuyCount() + buyCount); // Increment the buy count
                trackerRepository.save(tracker);

                // Get the current month
                YearMonth currentMonth = YearMonth.now();

                // Find or create the MonthlyTracker record for the current month
                Optional<MonthlyTracker> monthlyTrackerOptional = monthlyTrackerRepository.findByTrackerAndMonth(tracker, currentMonth.toString());
                if (monthlyTrackerOptional.isPresent()) {
                    MonthlyTracker monthlyTracker = monthlyTrackerOptional.get();
                    // Increment the count for the current month
                    monthlyTracker.setBuyCount(monthlyTracker.getBuyCount() + buyCount);
                    monthlyTrackerRepository.save(monthlyTracker);
                } else {
                    // If no entry exists for this month, create a new one
                    MonthlyTracker monthlyTracker = new MonthlyTracker();
                    monthlyTracker.setTracker(tracker);
                    monthlyTracker.setMonth(currentMonth.toString());
                    monthlyTracker.setBuyCount(buyCount);  // First Buy of the month
                    monthlyTrackerRepository.save(monthlyTracker);
                }

            }

            return ResponseEntity.ok("Buy count updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating buy count", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}