package com.solutions.sales.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

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
