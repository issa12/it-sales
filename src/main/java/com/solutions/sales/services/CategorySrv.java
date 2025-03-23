package com.solutions.sales.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.solutions.sales.entities.Category;
import com.solutions.sales.repos.CategoryRepository;
import lombok.extern.slf4j.Slf4j;

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
