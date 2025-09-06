package com.example.AffiliateAdda.Model;

import java.time.LocalDateTime;

public class Product {
    Long productId;
    String productName;
    String productBaseurl;
    String type;
    String subType;
    double perClickPrice = 0L;
    Long clickCount;
    double perBuyPrice = 0L;
    Long buyCount = 0L;
    boolean active = true;
    private LocalDateTime createdAt;



}
