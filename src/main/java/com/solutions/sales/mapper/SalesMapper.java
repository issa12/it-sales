package com.solutions.sales.mapper;

import com.solutions.sales.dto.ReqInvoice;
import com.solutions.sales.dto.ReqPurchasedProduct;
import com.solutions.sales.dto.ResInvoince;
import com.solutions.sales.exceptions.InvalidPayloadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SalesMapper class is responsible for mapping the payLoad to #ReqInvoice and
 * #ResInvoince to String.
 * Note: as we are using custom mapping we can register a mapper in Jackson to
 * handle the conversion
 * without calling the mapper explicitly in our code
 */
@Component
@Slf4j
public class SalesMapper {
    /*
     * Pattern to match the input string for each line in request payload
     * Pattern:
     * [Number] [Optional: Word] [Optional: imported] [Any Word] at [Decimal Number]
     *
     */
    final Pattern pattern = Pattern.compile("(\\d+)\\s+([a-zA-Z]+(\\s+[a-zA-Z]+)*)\\s+at\\s+(-?\\d+(\\.\\d+)?)");

    /**
     * convert the input string to ReqInvoice the format of the input string is as
     * follows
     * [Number] [imported] [Any Word] at [Decimal Number]
     * [Number] [Any Word] at [Decimal Number]
     * ...
     *
     * @param payLoad
     * @return
     */
    public ReqInvoice mapStringToReqInvoice(String payLoad) {
        log.debug("SalesMapper.mapStringToReqInvoice mapping payload to ReqInvoice: {}", payLoad);
        if (payLoad == null || payLoad.isEmpty()) {
            throw new InvalidPayloadException("Invalid input");
        }

        List<ReqPurchasedProduct> reqPurchasedProductList = new ArrayList<>();
        String[] lines = payLoad.split("\n");
        for (String line : lines) {
            /*
             * Careful! Imported word can be anywher : example:
             * 1 box of imported chocolates at 11.25
             */
            boolean isImported = line.contains(" imported ");
            line = line.replace("imported ", "");

            Matcher matcher = pattern.matcher(line.trim());
            if (matcher.find()) {
                BigDecimal unitPrice = (new BigDecimal(matcher.group(4))).setScale(2, RoundingMode.HALF_UP);
                // if imported is present then price will be at group 5 else at group 4
                reqPurchasedProductList.add(ReqPurchasedProduct.builder()
                        .quantity(Integer.parseInt(matcher.group(1)))
                        .imported(isImported)
                        .productName(matcher.group(2))
                        .price(unitPrice)
                        .build());

            }
        }
        log.debug("SalesMapper.mapStringToReqInvoice size : {}", reqPurchasedProductList.size());
        return ReqInvoice.builder().purchasedProducts(reqPurchasedProductList).build();
    }

    public String mapResInvoinceToString(ResInvoince resInvoince) {
        if (resInvoince == null || resInvoince.getPurchasedProducts().isEmpty()) {
            throw new IllegalArgumentException("Invalid Output!");
        }

        log.debug("SalesMapper.mapStringToSeqInvoice resInvoince size : {}", resInvoince.getPurchasedProducts().size());

        StringBuilder sb = new StringBuilder();
        resInvoince.getPurchasedProducts().forEach(purchasedProduct -> sb.append(purchasedProduct.getQuantity()).append(" ")
                .append(purchasedProduct.isImported() ? "Imported " : "")
                .append(purchasedProduct.getProductName()).append(" at ")
                .append(purchasedProduct.getPriceWithTax()).append("\n"));

        sb.append("Sales Taxes: ").append(resInvoince.getTotalTax()).append(" ")
                .append("Total: ").append(resInvoince.getTotalPriceWithTax());
        return sb.toString();
    }
}
