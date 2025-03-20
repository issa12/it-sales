package com.solutions.sales.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.sales.entities.Tax;
import com.solutions.sales.repos.CategoryRepository;
import com.solutions.sales.repos.ProductRepository;
import com.solutions.sales.repos.TaxRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sales")
@Slf4j
public class SalesTax {

    final private TaxRepository taxRepository;
    final private CategoryRepository categoryRepository;
    final private ProductRepository productRepository;

    public SalesTax(TaxRepository taxRepository, CategoryRepository categoryRepository,
            ProductRepository productRepository) {
        this.taxRepository = taxRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/tax")
    public String getSalesTaxe() {
        List<Tax> taxes = taxRepository.findAll();
        String ss = taxes.stream().map(t -> t.toString()).collect(Collectors.joining(","));

        return ss;
    }

    @PostMapping("")
    public String postMethodName(@RequestBody String entity) {

        return entity;
    }

}
