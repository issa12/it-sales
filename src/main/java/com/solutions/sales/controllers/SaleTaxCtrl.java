package com.solutions.sales.controllers;

import com.solutions.sales.dto.ReqInvoice;
import com.solutions.sales.dto.ResInvoince;
import com.solutions.sales.exceptions.InvalidPayloadException;
import com.solutions.sales.mapper.SalesMapper;
import com.solutions.sales.services.SaleTaxSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Slf4j
public class SaleTaxCtrl {
    private final SaleTaxSrv saleTaxSrv;
    private final SalesMapper salesMapper;

    public SaleTaxCtrl(SaleTaxSrv saleTaxSrv, SalesMapper salesMapper) {
        this.saleTaxSrv = saleTaxSrv;
        this.salesMapper = salesMapper;
    }

    @PostMapping("/invoice")
    public String postMethodName(@RequestBody String entity) {
        ReqInvoice reqInvoice = salesMapper.mapStringToReqInvoice(entity);
        log.debug("SaleTaxCtrl.postMethodName reqInvoice: {}", reqInvoice);
        if (reqInvoice == null || reqInvoice.getPurchasedProducts().isEmpty()) {
            throw new InvalidPayloadException("No Product found in payload");
        }
        ResInvoince resInvoince = saleTaxSrv.calculateTax(reqInvoice);
        return salesMapper.mapResInvoinceToString(resInvoince);
    }
}
