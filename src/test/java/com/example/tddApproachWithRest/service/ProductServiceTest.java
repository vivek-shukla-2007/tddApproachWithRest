package com.example.tddApproachWithRest.service;

import com.example.tddApproachWithRest.dto.Product;
import com.example.tddApproachWithRest.entity.ProductEntity;
import com.example.tddApproachWithRest.mapper.ProductMapper;
import com.example.tddApproachWithRest.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product productDto;
    private ProductEntity product;
    private ProductEntity savedProduct;

    @BeforeEach
    void setUp() {
        // Arrange: Prepare the test data
        productDto = new Product( "Test Product", new BigDecimal("100.0"),null);
        product = new ProductEntity(null,"Test Product", BigDecimal.valueOf(100.0));
        savedProduct = new ProductEntity(1L, "Test Product", BigDecimal.valueOf(100.0));
    }

    @Test
    void testSaveProduct_shouldSaveAndReturnProductDto() {
        // Arrange: Define the behavior of the mocks
        when(productMapper.toEntity(any(Product.class))).thenReturn(product);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(savedProduct);
        when(productMapper.toDto(any(ProductEntity.class))).thenReturn(new Product( "Test Product", BigDecimal.valueOf(100.0), 1L));

        // Act: Call the method we are testing
        Product result = productService.saveProduct(productDto);

        // Assert: Verify the results and interactions
        assertNotNull(result);
        assertEquals(1L, result.getProductId());
        assertEquals("Test Product", result.getProductName());

        // Verify that the repository's save method was called exactly once
        verify(productRepository, times(1)).save(product);

        // Verify that the mapper methods were called
        verify(productMapper, times(1)).toEntity(productDto);
        verify(productMapper, times(1)).toDto(savedProduct);
    }

}
