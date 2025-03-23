package com.solutions.sales.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.solutions.sales.mapper.SalesMapper;
import com.solutions.sales.services.SaleTaxSrv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(SaleTaxCtrl.class)
public class SaleTaxCntrlTest {

    @Autowired
    private MockMvc mockMvc;

    // @Mock
    // private TaxRepository taxRepository;

    // @Mock
    // private ProductRepository productRepository;

    // @Mock
    // private CategoryRepository categoryRepository;

    @MockBean
    private SaleTaxSrv saleTaxSrv;

    @MockBean
    private SalesMapper mapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostMethodName() throws Exception {
        String input = "1 imported book at 12.49\n 2 music CD With choclate Bar at 14.99\"n";
        mockMvc.perform(post("/v1/invoice")
                .content(input))
                .andExpect(status().isOk());


    }
}