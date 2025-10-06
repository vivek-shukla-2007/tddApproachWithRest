package com.example.tddApproachWithRest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value // Best for immutable DTOs. Generates final fields, constructor, getters, etc.
@AllArgsConstructor // Required for TestRestTemplate to deserialize
@Builder
public class Product {

    @NotBlank(message = "Product name cannot be blank")
    String productName;

    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Product price must be positive")
    BigDecimal productPrice;
    Long productId;

}
