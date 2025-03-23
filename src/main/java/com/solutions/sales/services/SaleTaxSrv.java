package com.solutions.sales.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.solutions.sales.dto.ReqInvoice;
import com.solutions.sales.dto.ReqPurchasedProduct;
import com.solutions.sales.dto.ResInvoince;
import com.solutions.sales.dto.ResPurchasedProduct;
import com.solutions.sales.entities.Product;
import com.solutions.sales.entities.Tax;
import com.solutions.sales.exceptions.DBException;
import com.solutions.sales.repos.ProductRepository;
import com.solutions.sales.repos.TaxRepository;

@Service
public class SaleTaxSrv {
    
    @Value("${sales.import.tax.name:imported}")
    private String importedTaxName;

    private final TaxRepository taxRepository;
    private final ProductRepository productRepository;

    public SaleTaxSrv(TaxRepository taxRepository, ProductRepository productRepository) {
        this.taxRepository = taxRepository;
        this.productRepository = productRepository;
    }

    public String getImportTaxName() {
        return importedTaxName;
    }
    public void setImportTaxName(String importedTaxName) {
        this.importedTaxName = importedTaxName; 
    }

    public ResInvoince calculateTax(final ReqInvoice reqInvoice) {
        /*
         * Collect Names to avoid multiple queries to the database
         */
        final Set<String> productNames = new HashSet<>();
        boolean imported = false;
        List<ReqPurchasedProduct> inputProds = reqInvoice.getPurchasedProducts();    
        for (final ReqPurchasedProduct purchasedProduct : inputProds) {
            productNames.add(purchasedProduct.getProductName());
            imported = imported || purchasedProduct.isImported();
        }
        
        final Tax importTax = imported ? getImportTax():null;

        // Map product name to product
        final Map<String, Product> productMap = new HashMap<>();
        List<Product>  products = productRepository.findByNameIn(productNames);
        products.forEach(product -> {
               productMap.put(product.getName(), product);
        });
        return  updateProductWithTax(inputProds, productMap, importTax);
        
    }

    private ResInvoince updateProductWithTax(List<ReqPurchasedProduct> purchasedProducts, Map<String, Product> products, Tax importTax) {
        List<ResPurchasedProduct> responseProducts = new ArrayList<>();
        BigDecimal importedRate = importTax == null ? BigDecimal.ZERO : importTax.getRate().divide(BigDecimal.valueOf(100));
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalPriceWithTax = BigDecimal.ZERO;
        for (ReqPurchasedProduct pProd : purchasedProducts) {
            Product p = products.get(pProd.getProductName());
            if (p == null) {
                throw new DBException(String.format("Product {}not found ", pProd.getProductName()));
            }   
            ResPurchasedProduct resPurchasedProduct =  buildRresPurchasedProd(pProd, p,importedRate, pProd.isImported());

            totalTax = totalTax.add(resPurchasedProduct.getTax());
            totalPriceWithTax = totalPriceWithTax.add(resPurchasedProduct.getPriceWithTax());

            responseProducts.add(resPurchasedProduct);
            
        }
        return ResInvoince.builder().purchasedProducts(responseProducts).totalTax(totalTax).totalPriceWithTax(totalPriceWithTax).build();
    }

    private ResPurchasedProduct buildRresPurchasedProd(ReqPurchasedProduct reqProd, Product prod, BigDecimal importedRate, Boolean imported) {
        BigDecimal taxRate = prod.getCategory().getTax() == null ? BigDecimal.ZERO : prod.getCategory().getTax().getRate().divide(BigDecimal.valueOf(100));
        BigDecimal priceWithoutTax = reqProd.getPrice().multiply(BigDecimal.valueOf(reqProd.getQuantity()));
        BigDecimal taxValue = priceWithoutTax.multiply(taxRate);
        BigDecimal importTaxValue = imported ? priceWithoutTax.multiply(importedRate): BigDecimal.ZERO;
        BigDecimal totalTax = taxValue.add(importTaxValue).setScale(2, RoundingMode.HALF_UP);

        return  ResPurchasedProduct.builder()
            .productName(reqProd.getProductName())
            .quantity(reqProd.getQuantity())
            .imported(reqProd.isImported())
            .price(reqProd.getPrice())
            .tax(totalTax)
            .priceWithTax(priceWithoutTax.add(totalTax)).build();
    }

    private Tax getImportTax() {
        Optional<Tax> imporTax = taxRepository.findByName(getImportTaxName());
        
        return imporTax.orElseGet(() -> {
            throw new DBException("Import Tax not found");
        });
    }


}
