package com.example.tddApproachWithRest.controller;

import com.example.tddApproachWithRest.dto.Product;
import com.example.tddApproachWithRest.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> storeProduct(@Valid @RequestBody Product product){
        product = productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED); // Return 201 Created
    }
}
