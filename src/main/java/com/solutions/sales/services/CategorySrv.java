package com.solutions.sales.services;

import com.solutions.sales.entities.Category;
import com.solutions.sales.repos.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategorySrv {
    private final CategoryRepository categoryRepository;
    public CategorySrv(CategoryRepository repo) {
        this.categoryRepository = repo;
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
}
