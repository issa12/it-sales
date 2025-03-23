package com.solutions.sales.controllers;

import com.solutions.sales.entities.Category;
import com.solutions.sales.services.CategorySrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     *
     * @return
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> getAllCategory() {
        return categorySrv.getAllCategories();
    }

}
