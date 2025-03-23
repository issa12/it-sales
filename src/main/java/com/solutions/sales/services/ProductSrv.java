package com.solutions.sales.services;

import com.solutions.sales.entities.Product;
import com.solutions.sales.repos.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductSrv {
    private final ProductRepository productRepository;

    public ProductSrv(ProductRepository repo) {
        this.productRepository = repo;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
}
