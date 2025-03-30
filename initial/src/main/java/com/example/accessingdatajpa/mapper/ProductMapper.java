package com.example.accessingdatajpa.mapper;

import com.example.accessingdatajpa.data.entity.Product;
import com.example.accessingdatajpa.rest.request.AddProductDto;
import com.example.accessingdatajpa.rest.response.ShortProductDto;

/**
 * Интерфейс маппера для преобразования между сущностями и DTO.
 * Используется для конвертации объектов Product и связанных DTO.
 *
 * @author Lobov-DI
 */
public interface ProductMapper {
    /**
     * Преобразует сущность Product в DTO ShortProductDto.
     *
     * @param product Сущность Product, которую необходимо преобразовать.
     *                Не должна быть null.
     * @return DTO ShortProductDto, содержащий основную информацию о продукте:
     *         ID, название, описание и цену.
     */
    ShortProductDto toShortProductDto(Product product);
    /**
     * Преобразует DTO AddProductDto в сущность Product.
     *
     * @param request DTO AddProductDto, содержащий данные для создания нового продукта.
     *                Не должен быть null.
     * @return Сущность Product, созданная на основе данных из DTO.
     *         ID сущности будет null, так как она еще не сохранена в базе данных.
     */
    Product toEntity(AddProductDto request);
}