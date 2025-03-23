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

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaxSrv {
    private final TaxRepository taxRepository;

    public TaxSrv(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    public List<Tax> getAllTaxes() {
        return taxRepository.findAll();
    }
    
}
