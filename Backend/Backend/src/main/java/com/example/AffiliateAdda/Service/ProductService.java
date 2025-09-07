package com.example.AffiliateAdda.Service;

import com.example.AffiliateAdda.DTO.ProductAdditionDTO;
import com.example.AffiliateAdda.DTO.ProductDTO;
import com.example.AffiliateAdda.DTO.ReviewDTO;
import com.example.AffiliateAdda.Model.Product;
import com.example.AffiliateAdda.Model.ProductDetail;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.ProductDetailRepository;
import com.example.AffiliateAdda.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;

    public Product getProductById(Long productId) {
        return productRepository.findByProductId(productId);
    }

    public Optional<Product> findByProductId(Long product_id) {
        return Optional.ofNullable(productRepository.findByProductId(product_id));
    }

    public List<String> getAllProductNames() {
        // Fetch all products and extract the product name
        System.out.println("Fetching the list of productName...(Service)");
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(Product::getProductName)  // Extract the product name
                .collect(Collectors.toList()); // Return the names as a list
    }

    public Optional<Product> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    public List<ProductDTO> getAllProducts() {
        // Fetch all products and convert to ProductDTO
        System.out.println("Fetching the list of products...(Service)");

        // Fetch all products from the repository
        List<Product> products = productRepository.findAll();

        // Choosing the commission (you can adjust as needed)
        double commission = 0.5;

        // Map the Product list to ProductDTO
        return products.stream()
                .map(product -> {
                    // Fetch the associated product detail
                    ProductDetail productDetail = product.getProductDetail();

                    // Fetch reviews for the product
                    List<ReviewDTO> reviewDTOs = product.getReviews().stream()
                            .map(review -> new ReviewDTO(review)) // Convert each review to ReviewDTO
                            .collect(Collectors.toList());

                    // Create and return the ProductDTO object with all details
                    return new ProductDTO(
                            product.getProductId(),                // productId
                            product.getProductName(),              // productName
                            product.getPerClickPrice() * commission, // perClickPrice
                            product.getType(),                     // productType
                            product.getSubType(),                  // productSubType
                            product.getPerBuyPrice() * commission,  // perBuyPrice
                            productDetail.getDescription(),        // description (from ProductDetail)
                            productDetail.getShortDescription(),   // shortDescription (from ProductDetail)
                            productDetail.getTags(),               // tags (from ProductDetail)
                            productDetail.getRating(),             // rating (from ProductDetail)
                            productDetail.getRatingCount(),
                            reviewDTOs                             // List of reviews for the product
                    );
                })
                .collect(Collectors.toList()); // Collect to list
    }

    public ProductAdditionDTO addProduct(ProductAdditionDTO productAdditionDTO, Optional<User> user) {
        Product product = new Product();
        product.setUser(user.get());
        product.setProductName(productAdditionDTO.getProductName());
        product.setProductBaseurl(productAdditionDTO.getProductBaseurl());
        product.setPerClickPrice(productAdditionDTO.getPerClickPrice());
        product.setPerBuyPrice(productAdditionDTO.getPerBuyPrice());
        product.setType(productAdditionDTO.getType());
        product.setSubType(productAdditionDTO.getSubType());
        product.setCreatedAt(LocalDateTime.now());

        ProductDetail productDetail = new ProductDetail();
        productDetail.setDescription(productAdditionDTO.getDescription());
        productDetail.setShortDescription(productAdditionDTO.getShortDescription());
        productDetail.setTags(productAdditionDTO.getTags());

        product.setProductDetail(productDetail);
        productDetail.setProduct(product);

        productRepository.save(product);
        productDetailRepository.save(productDetail);

        return productAdditionDTO;
    }


}