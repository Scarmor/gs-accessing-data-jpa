package com.example.accessingdatajpa.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * Сущность, представляющая продукт в системе.
 * Соответствует таблице "products" в базе данных.
 *
 * @author Lobov-DI
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    /**
     * Уникальный генерируемый идентификатор продукта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название продукта.
     */
    @NotEmpty(message = "Название продукта не может быть пустым")
    private String name;

    /**
     * Описание продукта.
     */
    @NotEmpty(message = "Описание продукта не может быть пустым")
    private String description;

    /**
     * Цена продукта.
     */
    @NotNull(message = "Цена продукта не может быть null")
    @Column(nullable = false)
    private BigDecimal price;
}
