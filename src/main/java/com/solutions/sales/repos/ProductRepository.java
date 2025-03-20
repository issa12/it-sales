package com.solutions.sales.repos;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutions.sales.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    Optional<Product> findByName(String name);
}
