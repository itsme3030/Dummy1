package com.example.AffiliateAdda.Controller;

import com.example.AffiliateAdda.DTO.ProductAdditionDTO;
import com.example.AffiliateAdda.DTO.ProductDTO;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.UserRepository;
import com.example.AffiliateAdda.Service.ActivationService;
import com.example.AffiliateAdda.Service.DeactivationService;
import com.example.AffiliateAdda.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeactivationService deactivationService;

    @Autowired
    private ActivationService activationService;

    // Endpoint to add a new product
    @PostMapping("/add")
    public ResponseEntity<ProductAdditionDTO> addProduct(@RequestBody ProductAdditionDTO productAdditionDTO, Principal principal) {

        //debug
        //System.out.println("------------------------------------------------->inside addProduct - controller : "+product);
        //System.out.println("Product received: " + product.getProductName() + ", " + product.getProductBaseurl() + ", " + product.getPerClickPrice());

        //Get User
        String username = principal.getName();

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body(productAdditionDTO);
        }

        ProductAdditionDTO savedProduct = productService.addProduct(productAdditionDTO,user);
        return ResponseEntity.ok(savedProduct);
    }

    // deactivate a product
    @PostMapping("/deactivateProduct/{productId}")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long productId) {
        try {
            deactivationService.deactivateProduct(productId, "Product deactivated");
            return new ResponseEntity<>("Product deactivated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error deactivating product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // activate a product
    @PostMapping("/activateProduct/{productId}")
    public ResponseEntity<String> activateProduct(@PathVariable Long productId) {
        try {
            activationService.activateProduct(productId, "Product activated");
            return new ResponseEntity<>("Product activated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error activating product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listProduct() {
        System.out.println("Fetching the list of products...(Controller)");
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}