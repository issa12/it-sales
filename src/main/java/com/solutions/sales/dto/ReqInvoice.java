package com.solutions.sales.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReqInvoice {
    List<ReqPurchasedProduct> purchasedProducts;

}