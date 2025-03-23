package com.solutions.sales.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.solutions.sales.dto.ReqInvoice;
import com.solutions.sales.dto.ReqPurchasedProduct;
import com.solutions.sales.dto.ResInvoince;
import com.solutions.sales.exceptions.InvalidPayloadException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SalesMapper {
        /**
         * Pattern to match the input string for each line in request payload
         * Pattern:
         * [Number] [Optional: imported] [Any Word] at [Decimal Number]
         * 
         */
        //final Pattern pattern = Pattern.compile( "(\\d+)\\s+(Imported\\s+)?(\\w+)\\s+at\\s+(\\d+)");
        //final Pattern pattern = Pattern.compile(  "(\\d+)\\s+(imported\\s+)?(\\w+)\\s+at\\s+(\\d*\\.?\\d*)");
        final Pattern pattern = Pattern.compile ("(\\d+)\\s+(imported\\s+)?([a-zA-Z]+(\\s+[a-zA-Z]+)*)\\s+at\\s+(-?\\d+(\\.\\d+)?)");
        

        public ReqInvoice mapStringToReqInvoice(String payLoad) {
            log.debug("SalesMapper.mapStringToReqInvoice mapping payload to ReqInvoice: {}", payLoad);
            if (payLoad == null || payLoad.isEmpty()) {
                throw new InvalidPayloadException("Invalid input");
            }
            
            List<ReqPurchasedProduct>  reqPurchasedProductList = new ArrayList<>();  
            String[] lines = payLoad.split("\n");
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line.trim());
                if (matcher.find()) {
                    // if imported is present then price will be at group 5 else at group 4
                    boolean isImported = matcher.group(2) != null;
                    BigDecimal price ;
                    if (isImported ) {
                        price = new BigDecimal(matcher.group(5));
                    }
                    else {
                        price = new BigDecimal(matcher.group(5));
                    }
                    
                    
                    reqPurchasedProductList.add(ReqPurchasedProduct.builder()
                            .quantity(Integer.parseInt(matcher.group(1)))
                            .imported(matcher.group(2) != null)
                            .productName(matcher.group(3))
                            .price(price)
                            .build());
                    
                }
            }
            log.debug("SalesMapper.mapStringToReqInvoice size : {}", reqPurchasedProductList.size());
            return ReqInvoice.builder().purchasedProducts(reqPurchasedProductList).build();  
        }


        public String mapStringToSeqInvoice(ResInvoince resInvoince) {
            if (resInvoince == null || resInvoince.getPurchasedProducts().isEmpty()) {
                throw new IllegalArgumentException("Invalid Output!");
            }
            
            log.debug("SalesMapper.mapStringToSeqInvoice resInvoince size : {}", resInvoince.getPurchasedProducts().size());

            StringBuilder sb = new StringBuilder();
            resInvoince.getPurchasedProducts().forEach(purchasedProduct -> {
                sb.append(purchasedProduct.getQuantity()).append(" ")
                .append(purchasedProduct.isImported() ? "Imported ": "")
                .append(purchasedProduct.getProductName()).append(" at ")
                .append(purchasedProduct.getPriceWithTax()).append("\n");
            });

            sb.append("Sales Taxes: ").append(resInvoince.getTotalTax()).append(" ")
                .append("Total: ").append(resInvoince.getTotalPriceWithTax());
            return sb.toString();  
        }
}
