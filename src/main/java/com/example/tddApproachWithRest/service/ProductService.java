package com.example.tddApproachWithRest.service;

import com.example.tddApproachWithRest.dto.Product;
import com.example.tddApproachWithRest.entity.ProductEntity;
import com.example.tddApproachWithRest.mapper.ProductMapper;
import com.example.tddApproachWithRest.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
     private final ProductRepository productRepository;
     private final ProductMapper productMapper;

    public Product saveProduct(Product product) {
        ProductEntity productEntity= productRepository.save(productMapper.toEntity(product));
        return productMapper.toDto(productEntity);
    }
}
