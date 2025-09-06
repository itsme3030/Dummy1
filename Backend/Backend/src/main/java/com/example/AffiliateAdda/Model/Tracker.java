package com.example.AffiliateAdda.Model;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

public class Tracker {
    Long tid;
    String productGeneratedUrl;
    Long count = 0L;
    Long buyCount = 0L;
    boolean active = true;
    private LocalDateTime createdAt;
    
}
