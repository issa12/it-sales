package com.solutions.sales.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.sales.entities.Tax;
import com.solutions.sales.repos.CategoryRepository;
import com.solutions.sales.repos.ProductRepository;
import com.solutions.sales.repos.TaxRepository;
import com.solutions.sales.services.SaleTaxSrv;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sales")
@Slf4j
public class SaleTaxCntrl {

    final private SaleTaxSrv saleTaxSrv;

    public SaleTaxCntrl(SaleTaxSrv saleTaxSrv) {
        this.saleTaxSrv = saleTaxSrv;
    }

    @GetMapping("/tax")
    public List<Tax>  getSalesTaxe() {
        return null;
    }
    @PostMapping("")
    public String postMethodName(@RequestBody String entity) {

        return entity;
    }

}
