package com.solutions.sales.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqPurchasedProduct {
    private String productName;
    private int quantity;
    private boolean imported;
    
    // Unit Price   
    private BigDecimal price;
}