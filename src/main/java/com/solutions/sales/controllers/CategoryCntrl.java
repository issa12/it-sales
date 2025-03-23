package com.solutions.sales.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.sales.entities.Category;
import com.solutions.sales.entities.Product;
import com.solutions.sales.entities.Tax;
import com.solutions.sales.repos.CategoryRepository;
import com.solutions.sales.services.CategorySrv;
import com.solutions.sales.services.ProductSrv;
import com.solutions.sales.services.TaxSrv;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryCntrl {

    final private CategorySrv categorySrv;

    public CategoryCntrl(CategorySrv srv) {
        this.categorySrv = srv;
    }

    /**
     * ToDO: create a DTO specific to return instead of returning Entities
     * @return
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategory() {
        return categorySrv.getAllCategories();
    }

}
