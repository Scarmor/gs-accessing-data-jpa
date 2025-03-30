package com.example.accessingdatajpa.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Сокращенная версия продукта для пользователя
 * @author Lobov-DI
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShortProductDto{
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
