package com.solutions.sales.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResInvoince {
    List<ResPurchasedProduct> purchasedProducts;
    private BigDecimal totalTax;
    private BigDecimal totalPriceWithTax;

}
