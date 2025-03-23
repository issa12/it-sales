package com.solutions.sales.controllers;

import com.solutions.sales.entities.Tax;
import com.solutions.sales.services.TaxSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/tax")
@Slf4j
public class TaxCntrl {
    private final TaxSrv taxSrv;
    public TaxCntrl(TaxSrv taxSrv) {
        this.taxSrv = taxSrv;
    }

    /**
     * ToDO: create a DTO specific to return instead of returning Entities
     *
     * @return
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tax> getAllTaxes() {
        return taxSrv.getAllTaxes();
    }

}
