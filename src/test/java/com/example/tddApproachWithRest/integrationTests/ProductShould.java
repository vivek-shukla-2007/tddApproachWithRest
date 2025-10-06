package com.example.tddApproachWithRest.integrationTests;

import com.example.tddApproachWithRest.dto.Product;
import com.example.tddApproachWithRest.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class ProductShould {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        // Clean up the database after each test to ensure test isolation
        productRepository.deleteAll();
    }

    @Test
    void testCreateProduct_EndToEnd() {
        // Arrange: Create the URL and the request body
        String url = "http://localhost:" + port + "/products";
        Product requestDto = new Product( "End-to-End Monitor", new BigDecimal("299.99"), null);

        // Act: Send a real HTTP POST request to the running application
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(url, requestDto, Product.class);

        // Assert (Phase 1): Check the HTTP response
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Product responseDto = responseEntity.getBody();
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getProductId()).isNotNull();
        assertThat(responseDto.getProductName()).isEqualTo("End-to-End Monitor");

        // Assert (Phase 2): Verify the data in the database directly
        // This confirms the entire flow worked, from controller to database.
        assertThat(productRepository.findById(responseDto.getProductId())).isPresent();
    }

    @Test
    void testCreateProduct_WithInvalidData_EndToEnd() {
        // Arrange: Create the URL and an invalid request body (blank name)
        String url = "http://localhost:" + port + "/products";
        Product requestDto = new Product( "", new BigDecimal("299.99"), null );

        // Act: Send the request
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(url, requestDto, Product.class);

        // Assert: Check for a 400 Bad Request status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // Assert that nothing was saved to the database
        assertThat(productRepository.count()).isZero();
    }
}
