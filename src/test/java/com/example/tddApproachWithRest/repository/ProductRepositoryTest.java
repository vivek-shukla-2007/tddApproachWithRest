package com.example.tddApproachWithRest.repository;

import com.example.tddApproachWithRest.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindById_whenSaved_thenCanBeFound() {
        // Arrange: Create a new product entity (without an ID)
        ProductEntity newProduct = new ProductEntity(null, "Gaming Mouse", new BigDecimal("49.99"));

        // Act: Persist the entity to the database. The 'persistAndFlush' method saves the entity
        // and makes it available for retrieval immediately. It returns the managed entity with its generated ID.
        ProductEntity savedProduct = entityManager.persistAndFlush(newProduct);

        // Act: Try to find the product using our repository method
        Optional<ProductEntity> foundProductOptional = productRepository.findById(savedProduct.getId());

        // Assert: Verify that the product was found and its data is correct
        assertThat(foundProductOptional).isPresent();
        ProductEntity foundProduct = foundProductOptional.get();
        assertThat(foundProduct.getId()).isEqualTo(savedProduct.getId());
        assertThat(foundProduct.getProductName()).isEqualTo("Gaming Mouse");
    }

}
