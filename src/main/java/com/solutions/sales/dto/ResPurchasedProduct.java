package com.solutions.sales.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResPurchasedProduct {
    private String productName;
    private int quantity;
    private boolean imported;
    private BigDecimal price;
    
    private BigDecimal tax;
    private BigDecimal priceWithTax;
}
