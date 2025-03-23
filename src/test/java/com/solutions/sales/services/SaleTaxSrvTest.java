package com.solutions.sales.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.solutions.sales.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.solutions.sales.TestUtils;
import com.solutions.sales.dto.ReqInvoice;
import com.solutions.sales.dto.ReqPurchasedProduct;
import com.solutions.sales.dto.ResInvoince;
import com.solutions.sales.dto.ResPurchasedProduct;
import com.solutions.sales.entities.Product;
import com.solutions.sales.entities.Tax;
import com.solutions.sales.exceptions.DBException;
import com.solutions.sales.repos.ProductRepo;
import com.solutions.sales.repos.TaxRepo;


class SaleTaxSrvTest {

    @Mock
    private TaxRepo taxRepository;

    @Mock
    private ProductRepo productRepository;

    @InjectMocks
    private SaleTaxSrv saleTaxService;

    private Tax taxImport ;
    private Tax taxGeneral ;
    private Category categoryGeneral; 
    private Product productGeneral ;
    private Category categoryNoTax;
    private Product productNoTax1;
    private Product productNoTax2;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initTestObjects();
    }

    void initTestObjects() 
    {
        saleTaxService.setImportTaxName("imported");
        /*
         * Mock Database Objects
         */
        taxImport = Tax.builder().name("imported").rate(BigDecimal.valueOf(5)).build();
        taxGeneral = Tax.builder().name("gernale").rate(BigDecimal.valueOf(20)).build();

        categoryGeneral =  Category.builder().name("categoryGeneral").tax(taxGeneral).build();
        
        categoryNoTax =  Category.builder().name("categoryNoTax").tax(null).build();
        
        productGeneral = Product.builder().name("productGeneral").category(categoryGeneral).build();
        productNoTax1 = Product.builder().name("productNoTax1").category(categoryNoTax).build();
        productNoTax2 = Product.builder().name("productNoTax2").category(categoryNoTax).build();
    }


    @Test
    void testCalculateTax_WithValidProductsAndImportTaxOnProd() {
        // Arrange
 
        ReqPurchasedProduct product1 = ReqPurchasedProduct.builder()
            .productName("productGeneral")
            .price(BigDecimal.valueOf(100))
            .quantity(2)
            .imported(true).build();

        List<ReqPurchasedProduct> inputProds = Arrays.asList(product1);
        
        Set<String> names = new HashSet<>(Arrays.asList("productGeneral"));
        when(taxRepository.findByName("imported")).thenReturn(Optional.of(taxImport));
        when(productRepository.findByNameIn(names)).thenReturn(Arrays.asList(productGeneral));
        
        // Act
        ResInvoince invoice = saleTaxService.calculateTax(ReqInvoice.builder().purchasedProducts(inputProds).build());

        // Assert
        assertEquals(1, invoice.getPurchasedProducts().size());
        // For decimal comparison, use compareTo method because of precision (scale)
        assertEquals(0, invoice.getPurchasedProducts().get(0).getTax().compareTo(BigDecimal.valueOf(50)));
        assertEquals(0, invoice.getPurchasedProducts().get(0).getPriceWithTax().compareTo(BigDecimal.valueOf(250)));
    }


    @Test
    void testCalculateTax_WithValidProductsAndImportTax() {
        // Arrange
        ReqPurchasedProduct product1 = ReqPurchasedProduct.builder()
            .productName("productGeneral")
            .price(BigDecimal.valueOf(100))
            .quantity(1)
            .imported(true).build();
        ReqPurchasedProduct product2 = ReqPurchasedProduct.builder()
            .productName("productNoTax1")
            .price(BigDecimal.valueOf(200))
            .quantity(1)
            .imported(false).build();
            ReqPurchasedProduct product3 = ReqPurchasedProduct.builder()
            .productName("productNoTax2")
            .price(BigDecimal.valueOf(300))
            .quantity(1)
            .imported(true).build();

        List<ReqPurchasedProduct> inputProds = Arrays.asList(product1, product2, product3);
        
        
        when(taxRepository.findByName("imported")).thenReturn(Optional.of(taxImport));
        Set<String> names = new HashSet<>(Arrays.asList("productGeneral", "productNoTax1", "productNoTax2"));
        when(productRepository.findByNameIn(names)).thenReturn(Arrays.asList(productGeneral, productNoTax1, productNoTax2));
        
        // Act
        ResInvoince invoice = saleTaxService.calculateTax(ReqInvoice.builder().purchasedProducts(inputProds).build());
        List<ResPurchasedProduct> list = invoice.getPurchasedProducts();

        // Assert
        assertEquals(3, invoice.getPurchasedProducts().size());
        // For decimal comparison, use compareTo method because of precision (scale)
        assertEquals(0, list.get(0).getTax().compareTo(BigDecimal.valueOf(25)),
            TestUtils.buildFailMessage("Error Comaparing Taxes ", 25 , list.get(0).getTax() ));
        assertEquals(0, list.get(1).getTax().compareTo(BigDecimal.valueOf(0)), 
            TestUtils.buildFailMessage("Error Comaparing Taxes ", 0 , list.get(1).getTax() ));
        assertEquals(0, list.get(2).getTax().compareTo(BigDecimal.valueOf(15)),
            TestUtils.buildFailMessage("Error Comaparing Taxes ", 15 , list.get(2).getTax() ));
        assertEquals(0, invoice.getTotalTax().compareTo(BigDecimal.valueOf(40)),
            TestUtils.buildFailMessage("Error Comaparing TotalTax ", 45 ,  invoice.getTotalTax()));
        assertEquals(0, invoice.getTotalPriceWithTax().compareTo(BigDecimal.valueOf(640)),
            TestUtils.buildFailMessage("Error TotalPriceWithTax ", 45 ,  invoice.getTotalTax()));

    }

    @Test
    void testCalculateTax_WithMissingProduct() {
        /*
         * Product Not Found
         */
        // Arrange
        ReqPurchasedProduct reqProduct = ReqPurchasedProduct.builder()
            .productName("Product Not Found")
            .price(BigDecimal.valueOf(100))
            .quantity(2)
            .imported(true).build();

        List<ReqPurchasedProduct> inputProds = Arrays.asList(reqProduct);
        
        when(taxRepository.findByName("imported")).thenReturn(Optional.of(taxImport));
        Set<String> names = new HashSet<>(Arrays.asList("productNoTax1"));
        when(productRepository.findByNameIn(names)).thenReturn(Arrays.asList(productGeneral));

        // Act & Assert
        assertThrows(DBException.class, () -> 
            saleTaxService.calculateTax(ReqInvoice.builder().purchasedProducts(inputProds).build()));
        
    }

    @Test
    void testCalculateTax_WithMissingImportTax() {
        // Arrange
        ReqPurchasedProduct product1 = ReqPurchasedProduct.builder()
            .productName("productGeneral")
            .price(BigDecimal.valueOf(100))
            .quantity(1)
            .imported(true).build();
        List<ReqPurchasedProduct> inputProds = Collections.singletonList(product1);

        Set<String> names = new HashSet<>(Arrays.asList("productGeneral", "productNoTax1", "productNoTax2"));
        when(productRepository.findByNameIn(names)).thenReturn(Arrays.asList(productGeneral, productNoTax1, productNoTax2));
        when(taxRepository.findByName("imported")).thenReturn(Optional.empty());

        // // Act & Assert
        assertThrows(DBException.class, () -> 
            saleTaxService.calculateTax(ReqInvoice.builder().purchasedProducts(inputProds).build()));

    }

}