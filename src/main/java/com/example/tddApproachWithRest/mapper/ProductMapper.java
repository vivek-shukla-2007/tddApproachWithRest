package com.example.tddApproachWithRest.mapper;


import com.example.tddApproachWithRest.dto.Product;
import com.example.tddApproachWithRest.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "productPrice", source = "product.productPrice")
    ProductEntity toEntity(Product product);


    @Mapping(target = "productName", source = "productEntity.productName")
    @Mapping(target = "productPrice", source = "productEntity.productPrice")
    @Mapping(target = "productId", source = "productEntity.id")
    Product toDto(ProductEntity productEntity);
}
