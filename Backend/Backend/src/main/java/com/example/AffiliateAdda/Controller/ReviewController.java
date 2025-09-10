package com.example.AffiliateAdda.Controller;

import com.example.AffiliateAdda.DTO.ReviewDTO;
import com.example.AffiliateAdda.DTO.ReviewSubmitDto;
import com.example.AffiliateAdda.Model.User;
import com.example.AffiliateAdda.Repository.UserRepository;
import com.example.AffiliateAdda.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserRepository userRepository;

    // Endpoint to submit or update a review
    @PostMapping("/submit")
    public ResponseEntity<ReviewDTO> submitReview(@RequestBody ReviewSubmitDto reviewSubmitDto) {

        //debug
        System.out.println("inside submitReview - controller");

        // Fetch the user based on username from the session storage
        User user = userRepository.findByUsername(reviewSubmitDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ReviewDTO reviewDTO = reviewService.submitReview(
                reviewSubmitDto.getProductId(),
                user.getId(),
                reviewSubmitDto.getRating(),
                reviewSubmitDto.getReviewText()
        );

        return ResponseEntity.ok(reviewDTO);
    }
}