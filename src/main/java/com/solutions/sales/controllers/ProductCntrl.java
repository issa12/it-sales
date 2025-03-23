package com.solutions.sales.controllers;

import com.solutions.sales.entities.Product;
import com.solutions.sales.services.ProductSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductCntrl {

    private final ProductSrv taxSrv;

    public ProductCntrl(ProductSrv srv) {
        this.taxSrv = srv;
    }

    /**
     * ToDO: create a DTO specific to return instead of returning Entities
     *
     * @return
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllTaxes() {
        return taxSrv.getAllProducts();
    }

}
