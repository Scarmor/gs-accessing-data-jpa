package com.example.accessingdatajpa.mapper;

import com.example.accessingdatajpa.data.entity.Product;
import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;
import org.springframework.stereotype.Component;

/**
 * Маппер для преобразования между сущностями и DTO.
 * Используется для конвертации объектов Product и связанных DTO.
 *
 * @author Lobov-DI
 */
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ShortProductDto toShortProductDto(Product product) {
        ShortProductDto dto = new ShortProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        return dto;
    }

    @Override
    public Product toEntity(AddProductDto request) {
        Product product = new Product();
        product.setName(request.getName());
        return product;
    }
}