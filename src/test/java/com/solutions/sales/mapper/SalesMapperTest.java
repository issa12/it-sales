package com.solutions.sales.mapper;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.solutions.sales.dto.ReqInvoice;
import com.solutions.sales.dto.ReqPurchasedProduct;
import com.solutions.sales.dto.ResInvoince;
import com.solutions.sales.dto.ResPurchasedProduct;
import com.solutions.sales.exceptions.InvalidPayloadException;

class SalesMapperTest {

    @InjectMocks
    private SalesMapper salesMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapStringToReqInvoice_ValidPayload() {
        // Arrange
        String payload = "1 imported book at 12.49\n 2 music CD With choclate Bar at 14.99\n ";

        // Act
        ReqInvoice reqInvoice = salesMapper.mapStringToReqInvoice(payload);

        // Assert
        assertNotNull(reqInvoice);
        assertEquals(2, reqInvoice.getPurchasedProducts().size());

        ReqPurchasedProduct product1 = reqInvoice.getPurchasedProducts().get(0);
        assertEquals(1, product1.getQuantity());
        assertTrue(product1.isImported());
        assertEquals("book", product1.getProductName());
        assertEquals(new BigDecimal("12.49"), product1.getPrice());

        ReqPurchasedProduct product2 = reqInvoice.getPurchasedProducts().get(1);
        assertEquals(2, product2.getQuantity());
        assertFalse(product2.isImported());
        assertEquals("music CD With choclate Bar", product2.getProductName());
        assertEquals(new BigDecimal("14.99"), product2.getPrice());
    }

    @Test
    void testMapStringToReqInvoice_EmptyPayload() {
        String payload = "";
        assertThrows(InvalidPayloadException.class, () -> salesMapper.mapStringToReqInvoice(payload));
    }

    @Test
    void testMapStringToReqInvoice_MalformedPayload() {
        String payload = "invalid payload";

        ReqInvoice reqInvoice = salesMapper.mapStringToReqInvoice(payload);

        assertNotNull(reqInvoice);
        assertTrue(reqInvoice.getPurchasedProducts().isEmpty());
    }

    @Test
    void testMapStringToSeqInvoice_ValidResponse() {
        ResPurchasedProduct product1 = ResPurchasedProduct.builder()
                .quantity(1)
                .imported(true)
                .productName("book")
                .priceWithTax(new BigDecimal("13.49"))
                .build();

        ResPurchasedProduct product2 = ResPurchasedProduct.builder()
                .quantity(2)
                .imported(false)
                .productName("music CD")
                .priceWithTax(new BigDecimal("16.49"))
                .build();

        ResInvoince resInvoince = ResInvoince.builder()
                .purchasedProducts(Arrays.asList(product1, product2))
                .totalTax(new BigDecimal("2.50"))
                .totalPriceWithTax(new BigDecimal("29.98"))
                .build();

        String result = salesMapper.mapResInvoinceToString(resInvoince);

        String expected = "1 Imported book at 13.49\n" +
                          "2 music CD at 16.49\n" +
                          "Sales Taxes: 2.50 Total: 29.98";

        assertEquals(expected, result);
    }

    @Test
    void testMapStringToSeqInvoice_InvalidResponse() {
        ResInvoince resInvoince = ResInvoince.builder()
                .purchasedProducts(Collections.emptyList())
                .build();

        assertThrows(IllegalArgumentException.class, () -> salesMapper.mapResInvoinceToString(resInvoince));
    }
}