package com.solutions.sales.services;

import com.solutions.sales.entities.Tax;
import com.solutions.sales.repos.TaxRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaxSrv {
    private final TaxRepo taxRepository;

    public TaxSrv(TaxRepo taxRepository) {
        this.taxRepository = taxRepository;
    }

    public List<Tax> getAllTaxes() {
        return taxRepository.findAll();
    }
    
}
