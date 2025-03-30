package com.example.accessingdatajpa.rest.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO для запроса на добавление продукта
 * @author Lobov-DI
 */
@AllArgsConstructor
@Data
@Builder
public class AddProductDto {
    @NotNull(message = "name не должно быть равно null")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\\\s-]{0,254}$", message = "name не соответсвует паттерну")
    private String name;
    @NotNull(message = "description не должно быть равно null")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ0-9\\\\s.,!?;:\"()\\-]+$", message = "description не соответсвует паттерну")
    private String description;
    @NotNull(message = "price не должно быть равно null")
    private BigDecimal price;
}
