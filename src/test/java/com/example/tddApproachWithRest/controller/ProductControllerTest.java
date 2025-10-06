package com.example.tddApproachWithRest.controller;

import com.example.tddApproachWithRest.dto.Product;
import com.example.tddApproachWithRest.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @MockitoBean
    private ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .productPrice(BigDecimal.valueOf(2.0))
                .productName("phone")
                .build();
    }

    @Test
    void saveProductTest() throws Exception {
        String productJson = objectMapper.writeValueAsString(product);
        Product savedProduct = Product.builder()
                .productId(1L)
                .productName("phone")
                .productPrice(BigDecimal.valueOf(2.0))
                .build();

        when(productService.saveProduct(ArgumentMatchers.any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.productName").value("phone"))
                .andExpect(jsonPath("$.productPrice").value(2.0));
    }

    @Test
    void testCreateProduct_whenInvalidProduct_shouldReturnBadRequest() throws Exception {
        Product invalidProductDto = new Product("phone", BigDecimal.valueOf(-2.5), null);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProductDto)))
                .andExpect(status().isBadRequest());
    }
}
