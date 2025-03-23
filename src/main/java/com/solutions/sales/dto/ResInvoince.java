package com.solutions.sales.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ResInvoince {
    List<ResPurchasedProduct> purchasedProducts;
    private BigDecimal totalTax;
    private BigDecimal totalPriceWithTax;

}
